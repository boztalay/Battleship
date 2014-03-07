package com.boztalay.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 *  Responsible mainly for handling user interaction
 *  and running the game loop.
 */
public class Battleship {
    private Player player1;
    private Player player2;

    public void playBattleship() throws IOException {
        System.out.println("Welcome to Battleship!\n");

        System.out.print("Player 1, what's your name? ");
        String player1Name = ConsoleUtils.readLine();
        player1 = new Player(player1Name);

        System.out.print("Player 2, what's your name? ");
        String player2Name = ConsoleUtils.readLine();
        player2 = new Player(player2Name);

        runShipPlacementForPlayer(player1);
        ConsoleUtils.clearConsole();
        runShipPlacementForPlayer(player2);
        ConsoleUtils.clearConsole();

        //Game loop here

        //End game
    }

    private void runShipPlacementForPlayer(Player player) throws IOException {
        System.out.println("\n" + player.getName() + ", place your ships!");
        System.out.println("For each ship, specify its placement with \"x,y,orientation\"");
        System.out.println("For example, a vertical ship at 2,2 would be \"2,2," + Ship.ShipOrientation.VERTICAL.shortName + "\". Use '" + Ship.ShipOrientation.HORIZONTAL.shortName + "' for horizontal.");
        System.out.println("Press enter to continue");
        ConsoleUtils.waitForUserToPressEnter();

        Ship.ShipType[] shipsToPlace = {
                Ship.ShipType.CARRIER,
                Ship.ShipType.BATTLESHIP,
                Ship.ShipType.SUBMARINE,
                Ship.ShipType.CRUISER,
                Ship.ShipType.DESTROYER,
        };

        for(Ship.ShipType shipTypeToPlace : shipsToPlace) {
            FieldDisplay.displayFieldForPlayer(player);

            while(true) {
                System.out.print("Place your " + shipTypeToPlace.name + " at: ");
                String placement = ConsoleUtils.readLine();

                try {
                    if(!isPlacementStringValid(placement)) {
                        throw new Field.InvalidShipPlacementException();
                    } else {
                        Ship shipToPlace = parsePlacementStringIntoShip(placement, shipTypeToPlace);
                        player.placeShip(shipToPlace);
                        break;
                    }
                } catch(Field.InvalidShipPlacementException e) {
                    System.out.println("That wasn't a valid placement!");
                }
            }

            System.out.println();
        }

        FieldDisplay.displayFieldForPlayer(player);
        System.out.println("All of your ships have been placed! Press enter to continue");
        ConsoleUtils.waitForUserToPressEnter();
    }

    private boolean isPlacementStringValid(String placement) {
        return Pattern.matches("[0-9]+,[0-9]+,(" + Ship.ShipOrientation.HORIZONTAL.shortName + "|" + Ship.ShipOrientation.VERTICAL.shortName + ")", placement);
    }

    private Ship parsePlacementStringIntoShip(String placement, Ship.ShipType shipType) {
        String[] placementComponents = placement.split(",");

        int shipX = Integer.valueOf(placementComponents[0]);
        int shipY = Integer.valueOf(placementComponents[1]);

        Ship.ShipOrientation shipOrientation = Ship.ShipOrientation.HORIZONTAL;
        if(placementComponents[2].equals(String.valueOf(Ship.ShipOrientation.VERTICAL.shortName))) {
            shipOrientation = Ship.ShipOrientation.VERTICAL;
        }

        return new Ship(shipX, shipY, shipOrientation, shipType);
    }
}
