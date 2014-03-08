package com.boztalay.battleship.cli;

import com.boztalay.battleship.engine.Field;
import com.boztalay.battleship.engine.Player;
import com.boztalay.battleship.engine.Ship;

public class FieldDisplay {
    private static final char SHIP_CHAR = 'S';

    public static void displayFieldForPlayer(Player player) {
        char[][] fieldToDisplay = buildCharacterFieldFromField(player.getField());
        drawShipsInFieldOnCharacterField(player.getField(), fieldToDisplay);
        displayCharacterField(player.getName() + "'s field", fieldToDisplay);
    }

    public static void displayFieldForPlayerWithoutShips(Player player) {
        char[][] fieldToDisplay = buildCharacterFieldFromField(player.getField());
        displayCharacterField(player.getName() + "'s field - Without ships", fieldToDisplay);
    }

    private static char[][] buildCharacterFieldFromField(Field field) {
        char[][] charField = new char[field.getSize()][field.getSize()];

        //Lay down all of the base characters from the field
        for(int x = 0; x < field.getSize(); x++) {
            for(int y = 0; y < field.getSize(); y++) {
                charField[x][y] = field.getSpaceAt(x, y).displayChar;
            }
        }

        return charField;
    }

    private static void drawShipsInFieldOnCharacterField(Field field, char[][] charField) {
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
    }

    //Assumes the field's dimensions are both less than 100
    private static void displayCharacterField(String fieldLabel, char[][] field) {
        System.out.println(fieldLabel);

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
