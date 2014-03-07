package com.boztalay.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    private void runShipPlacementForPlayer(Player player) {
        System.out.println(player.getName() + ", place your ships! Press enter to continue");
        waitForUserToPressEnter();

        Ship[] shipsToPlace = new Ship[5];
        shipsToPlace[0] = new Ship(0, 0, Ship.ShipOrientation.HORIZONTAL, Ship.ShipType.CARRIER);
        shipsToPlace[1] = new Ship(0, 0, Ship.ShipOrientation.HORIZONTAL, Ship.ShipType.BATTLESHIP);
        shipsToPlace[2] = new Ship(0, 0, Ship.ShipOrientation.HORIZONTAL, Ship.ShipType.SUBMARINE);
        shipsToPlace[3] = new Ship(0, 0, Ship.ShipOrientation.HORIZONTAL, Ship.ShipType.CRUISER);
        shipsToPlace[4] = new Ship(0, 0, Ship.ShipOrientation.HORIZONTAL, Ship.ShipType.DESTROYER);

        for(Ship ship : shipsToPlace) {
            FieldDisplay.displayField(player.getField());
            waitForUserToPressEnter();
        }
    }

    private void waitForUserToPressEnter() {
        try {
            System.in.read();
        } catch (IOException e) {
            //Do nothing
        }
    }
}
