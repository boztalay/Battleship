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

    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public void playBattleship() throws IOException {
        System.out.println("Welcome to Battleship!\n");

        System.out.print("Player 1, what's your name? ");
        String player1Name = input.readLine();
        player1 = new Player(player1Name);

        System.out.print("Player 2, what's your name? ");
        String player2Name = input.readLine();
        player2 = new Player(player2Name);

        runShipPlacementForPlayer(player1);
        runShipPlacementForPlayer(player2);

        //Game loop here

        //End game
    }

    private void runShipPlacementForPlayer(Player player) throws IOException {
        System.out.println(player.getName() + ", place your ships! Press enter to continue");
        waitForUserToPressEnter();

        Ship.ShipType[] shipsToPlace = new Ship.ShipType[5];
        shipsToPlace[0] = Ship.ShipType.CARRIER;
        shipsToPlace[1] = Ship.ShipType.BATTLESHIP;
        shipsToPlace[2] = Ship.ShipType.SUBMARINE;
        shipsToPlace[3] = Ship.ShipType.CRUISER;
        shipsToPlace[4] = Ship.ShipType.DESTROYER;

        for(Ship.ShipType shipTypeToPlace : shipsToPlace) {
            FieldDisplay.displayField(player.getField());

            while(true) {
                System.out.print("Place your " + shipTypeToPlace.name + " at (format: x,y,[H|V]): ");
                String placement = input.readLine();

                if(!isPlacementStringValid(placement)) {
                    System.out.println("That wasn't a valid placement!");
                } else {
                    Ship shipToPlace = parsePlacementStringIntoShip(placement, shipTypeToPlace);

                    try {
                        player.placeShip(shipToPlace);
                    } catch(Field.InvalidShipPlacementException e) {
                        System.out.println("That wasn't a valid placement!");
                        continue;
                    }

                    break;
                }
            }
        }
    }

    private void waitForUserToPressEnter() {
        try {
            input.readLine();
        } catch (IOException e) {
            //Do nothing
        }
    }

    private boolean isPlacementStringValid(String placement) {
        return Pattern.matches("[0-9]+,[0-9]+,(H|V)", placement);
    }

    private Ship parsePlacementStringIntoShip(String placement, Ship.ShipType shipType) {
        String[] placementComponents = placement.split(",");

        int shipX = Integer.valueOf(placementComponents[0]);
        int shipY = Integer.valueOf(placementComponents[1]);

        Ship.ShipOrientation shipOrientation = Ship.ShipOrientation.HORIZONTAL;
        if(placementComponents[2].equals("V")) {
            shipOrientation = Ship.ShipOrientation.VERTICAL;
        }

        return new Ship(shipX, shipY, shipOrientation, shipType);
    }
}
