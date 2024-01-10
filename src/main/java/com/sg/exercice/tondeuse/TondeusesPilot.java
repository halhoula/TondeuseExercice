package com.sg.exercice.tondeuse;

import com.sg.exercice.tondeuse.exceptions.ConfigurationFileNotValid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TondeusesPilot {

    private static final String SPACE_SEPARATOR = " ";
    private List<Tondeuse> tondeuses;
    private File configurationFile;

    public TondeusesPilot(List<Tondeuse> tondeuses, File configurationFile) {
        this.tondeuses = tondeuses;
        this.configurationFile = configurationFile;
    }

    public void startTondeuses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(configurationFile))) {
            String[] pelouseDimensions = readPelouseDimensions(reader);
            List<String> configurations = readConfigurations(reader);

            intiliserTondeuses(configurations);
            executerInstructions(pelouseDimensions);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier d'instructions : " + e.getMessage());
        }
    }

    private static List<String> readConfigurations(BufferedReader reader) throws IOException {
        String line;
        List<String> configurations = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            configurations.add(line);
        }
        return configurations;
    }

    private static String[] readPelouseDimensions(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        String[] pelouseDimensions = new String[2];
        if (line != null) {
            pelouseDimensions = line.split(SPACE_SEPARATOR);
        }
        return pelouseDimensions;
    }

    private void intiliserTondeuses(List<String> configurations) {
        validateConfiguration( configurations);
        int tondeuseIndex = 0;
        for (int i = 0; i < configurations.size(); i += 2) {
            String positionInitiale = configurations.get(i);
            String instructions = configurations.get(i + 1);
            Tondeuse tondeuse = tondeuses.get(tondeuseIndex);
            tondeuse.initialiserTondeuse(tondeuse, positionInitiale, instructions.toCharArray());
            tondeuseIndex++;
        }
    }

    private void validateConfiguration(List<String> configurations){
        if(configurations.size()%2 != 0 || configurations.size()/2 != tondeuses.size()){
            throw new ConfigurationFileNotValid();
        }

    }

    private void executerInstructions(String[] pelouseDimensions) {
        for (Tondeuse tondeuse : tondeuses) {
            tondeuse.executerInstructions(Integer.parseInt(pelouseDimensions[0]), Integer.parseInt(pelouseDimensions[1]));
        }
    }

}
