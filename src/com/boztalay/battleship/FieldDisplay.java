package com.boztalay.battleship;

public class FieldDisplay {
    private static final char SHIP_CHAR = 'S';

    public static void displayFieldForPlayer(Player player) {
        char[][] fieldToDisplay = buildCharacterFieldFromField(player.getField());
        System.out.println(player.getName() + "'s field");
        displayField(fieldToDisplay);
    }

    private static char[][] buildCharacterFieldFromField(Field field) {
        char[][] charField = new char[field.getSize()][field.getSize()];

        //Lay down all of the base characters from the field
        for(int x = 0; x < field.getSize(); x++) {
            for(int y = 0; y < field.getSize(); y++) {
                charField[x][y] = field.getSpaceAt(x, y).displayChar;
            }
        }

        //Draw the ships on top of it, a poor man's drawing buffer
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

    //Assumes the field's dimensions are both less than 100
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
