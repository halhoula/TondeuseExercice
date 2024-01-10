package com.sg.exercice.tondeuse;

public class Tondeuse {

    private static final String SPACE_SEPARATOR = " ";
    private Position position;
    private char[] instructions;

    public Tondeuse(Position position) {
        this.position = position;
    }

    public Tondeuse() {
    }

    public void avancer(int maxX, int maxY) {
        if (position.getOrientation() == 'N' && position.getY() < maxY) {
            position.setY(position.getY() + 1);
        } else if (position.getOrientation() == 'E' && position.getX() < maxX) {
            position.setX(position.getX() + 1);
        } else if (position.getOrientation() == 'S' && position.getY() > 0) {
            position.setY(position.getY() - 1);
        } else if (position.getOrientation() == 'W' && position.getX() > 0) {
            position.setX(position.getX() - 1);
        }
    }

    public void pivoterGauche() {
        switch (position.getOrientation()) {
            case 'N':
                position.setOrientation('W');
                break;
            case 'E':
                position.setOrientation('N');
                break;
            case 'S':
                position.setOrientation('E');
                break;
            case 'W':
                position.setOrientation('S');
                break;
        }
    }

    public void pivoterDroite() {
        switch (position.getOrientation()) {
            case 'N':
                position.setOrientation('E');
                break;
            case 'E':
                position.setOrientation('S');
                break;
            case 'S':
                position.setOrientation('W');
                break;
            case 'W':
                position.setOrientation('N');
                break;
        }
    }

    public void initialiserTondeuse(Tondeuse tondeuse, String initialPosition, char[] instructions) {
        String[] positionParts = initialPosition.split(SPACE_SEPARATOR);
        Position position = new Position(Integer.parseInt(positionParts[0]), Integer.parseInt(positionParts[1]), positionParts[2].charAt(0));
        tondeuse.setPosition(position);
        tondeuse.setInstructions(instructions);
    }

    public void executerInstructions(int maxX, int maxY) {
        for (char instruction : instructions) {
            if (instruction == 'G') {
                pivoterGauche();
            } else if (instruction == 'D') {
                pivoterDroite();
            } else if (instruction == 'A') {
                avancer(maxX, maxY);
            }
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setInstructions(char[] instructions) {
        this.instructions = instructions;
    }
}
