import com.sg.exercice.tondeuse.Tondeuse;
import com.sg.exercice.tondeuse.TondeusesPilot;
import com.sg.exercice.tondeuse.exceptions.ConfigurationFileNotValid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TondeuseTest {

    private TondeusesPilot tondeusesPilot;
    private List<Tondeuse> tondeuses;

    @BeforeEach
    public void setUp() {
        tondeuses = new ArrayList<>();

        Tondeuse tondeuse1 = new Tondeuse();
        Tondeuse tondeuse2 = new Tondeuse();

        tondeuses.add(tondeuse1);
        tondeuses.add(tondeuse2);

        File configurationFile = intializeConfigurationFile();
        tondeusesPilot = new TondeusesPilot(tondeuses, configurationFile);
    }

    @Test
    public void testStartTondeuses() {
        tondeusesPilot.startTondeuses();
        assertEquals("1 3 N", tondeuses.get(0).getPosition().toString());
        assertEquals("5 1 E", tondeuses.get(1).getPosition().toString());
    }

    @Test
    public void testConfigurationFileNotValid() {
        tondeuses.remove(1);
        assertThrows(ConfigurationFileNotValid.class, () -> {
            tondeusesPilot.startTondeuses();
        });

    }

    private File intializeConfigurationFile() {
        String confFileContent = """
                5 5
                1 2 N
                GAGAGAGAA
                3 3 E
                AADAADADDA
                                """;
        File file = new File("instructions.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(confFileContent);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
        return file;
    }
}
