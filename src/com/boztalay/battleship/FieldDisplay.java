package com.boztalay.battleship;

public class FieldDisplay {
    private static final char SHIP_CHAR = 'S';

    public static void displayField(Field field) {
        char[][] fieldToDisplay = buildCharacterFieldFromField(field);
        displayField(fieldToDisplay);
    }

    private static char[][] buildCharacterFieldFromField(Field field) {
        char[][] charField = new char[field.getSize()][field.getSize()];

        for(int x = 0; x < field.getSize(); x++) {
            for(int y = 0; y < field.getSize(); y++) {
                charField[x][y] = field.getSpaceAt(x, y).displayChar;
            }
        }

        for(int i = 0; i < field.getNumberOfShips(); i++) {
            Ship shipToDisplay = field.getShip(i);

            for(int j = 0; j < shipToDisplay.getLength(); j++) {
                int currentXOnField = shipToDisplay.getX();
                int currentYOnField = shipToDisplay.getY();

                if(shipToDisplay.getOrientation() == Ship.ShipOrientation.HORIZONTAL) {
                    currentXOnField += j;
                } else {
                    currentYOnField += j;
                }

                if(field.getSpaceAt(currentXOnField, currentYOnField) != Field.SpaceType.HIT) {
                    charField[currentXOnField][currentYOnField] = SHIP_CHAR;
                }
            }
        }

        return charField;
    }

    public static void displayField(char[][] field) {
        System.out.print("   ");
        for(int i = 0; i < field.length; i++) {
            printIntWithMinimumTwoDigitsAndTrailingSpace(i);
        }
        System.out.print('\n');

        for(int y = 0; y < field[0].length; y++) {
            printIntWithMinimumTwoDigitsAndTrailingSpace(y);

            for(int x = 0; x < field.length; x++) {
                System.out.print(" " + field[x][y] + " ");
            }
            System.out.print('\n');
        }
    }

    private static void printIntWithMinimumTwoDigitsAndTrailingSpace(int i) {
        if(i < 10) {
            System.out.print(' ');
        }
        System.out.print(i + " ");
    }
}
