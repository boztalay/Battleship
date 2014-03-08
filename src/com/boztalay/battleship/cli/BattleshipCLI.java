package com.boztalay.battleship.cli;

import com.boztalay.battleship.engine.BattleshipEngine;
import com.boztalay.battleship.engine.Field;
import com.boztalay.battleship.engine.Player;
import com.boztalay.battleship.engine.Ship;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Responsible mainly for handling user interaction
 * and running the game loop.
 */
public class BattleshipCLI {
    private static final int FIELD_SIZE = 10;
    private static final int NUM_PLAYERS = 2;

    private BattleshipEngine battleshipEngine;

    public void playBattleship() throws IOException, BattleshipEngine.WrongStateException {
        System.out.println("Welcome to Battleship!\n");

        initializeBattleshipEngineAndCreatePlayers();
        placeShips();
        playGame();
    }

    private void initializeBattleshipEngineAndCreatePlayers() throws BattleshipEngine.WrongStateException {
        battleshipEngine = new BattleshipEngine();
        battleshipEngine.setFieldSize(FIELD_SIZE);
        battleshipEngine.addShipTypeToPlace(Ship.ShipType.CARRIER);
        battleshipEngine.addShipTypeToPlace(Ship.ShipType.BATTLESHIP);
        battleshipEngine.addShipTypeToPlace(Ship.ShipType.SUBMARINE);
        battleshipEngine.addShipTypeToPlace(Ship.ShipType.CRUISER);
        battleshipEngine.addShipTypeToPlace(Ship.ShipType.DESTROYER);

        for(int i = 1; i <= NUM_PLAYERS; i++) {
            System.out.print("Player " + i + ", what's your name? ");
            String playerName = ConsoleUtils.readLine();
            battleshipEngine.createPlayer(playerName);
        }
    }

    private void placeShips() throws BattleshipEngine.WrongStateException {
        battleshipEngine.beginPlacement();

        while(!battleshipEngine.haveAllPlayersFinishedPlacing()) {
            battleshipEngine.startPlacingShipsForNextPlayer();
            Player player = battleshipEngine.getPlayerPlacingShips();

            System.out.println("\n" + player.getName() + ", place your ships!");
            System.out.println("For each ship, specify its placement with \"x,y,orientation\"");
            System.out.println("For example, a vertical ship at 2,2 would be \"2,2," + Ship.ShipOrientation.VERTICAL.shortName + "\". Use '" + Ship.ShipOrientation.HORIZONTAL.shortName + "' for horizontal.");
            System.out.println("Press enter to continue");
            ConsoleUtils.waitForUserToPressEnter();

            placeShipsForCurrentPlayer(player);

            FieldDisplay.displayFieldForPlayer(player);
            System.out.println("All of your ships have been placed! Press enter to continue");
            ConsoleUtils.waitForUserToPressEnter();

            ConsoleUtils.clearConsole();
        }
    }

    private void placeShipsForCurrentPlayer(Player player) throws BattleshipEngine.WrongStateException {
        while(!battleshipEngine.hasPlayerFinishedPlacing()) {
            Ship.ShipType shipTypeToPlace = battleshipEngine.getNextShipTypeToPlace();
            FieldDisplay.displayFieldForPlayer(player);

            while(true) {
                System.out.print("Place your " + shipTypeToPlace.name + " at: ");
                String placement = ConsoleUtils.readLine();

                try {
                    if(!isPlacementStringValid(placement)) {
                        throw new Field.InvalidShipPlacementException();
                    } else {
                        Ship shipToPlace = parsePlacementStringIntoShip(placement, shipTypeToPlace);
                        battleshipEngine.placeShipForCurrentPlayer(shipToPlace);
                        break;
                    }
                } catch(Field.InvalidShipPlacementException e) {
                    System.out.println("That wasn't a valid placement!");
                }
            }

            System.out.println();
        }
    }

    private void playGame() throws BattleshipEngine.WrongStateException {
        battleshipEngine.beginPlaying();

        while(true) {
            battleshipEngine.prepareForRound();

            while(!battleshipEngine.haveAllPlayersTakenTheirTurn()) {
                battleshipEngine.startNextPlayersTurn();
                Player playerGoing = battleshipEngine.getPlayerTakingTurn();

                if(playerGoing.areAllShipsSunk()) {
                    continue;
                }

                playTurnForCurrentPlayer(playerGoing);

                ConsoleUtils.clearConsole();

                Player winningPlayer = battleshipEngine.getWinningPlayer();
                if(winningPlayer != null) {
                    displayWinner(winningPlayer);
                    return;
                }
            }
        }
    }

    private void playTurnForCurrentPlayer(Player playerGoing) throws BattleshipEngine.WrongStateException {
        System.out.println(playerGoing.getName() + ", you're up! Press enter to continue");
        ConsoleUtils.waitForUserToPressEnter();

        while(!battleshipEngine.haveAllEnemyPlayersBeenFiredUpon()) {
            battleshipEngine.advanceToNextEnemyPlayer();
            Player enemyPlayer = battleshipEngine.getCurrentEnemyPlayer();

            if(enemyPlayer.areAllShipsSunk()) {
                continue;
            }

            fireAtCurrentEnemy(playerGoing, enemyPlayer);
        }
    }

    private void fireAtCurrentEnemy(Player playerGoing, Player enemyPlayer) {
        FieldDisplay.displayFieldForPlayer(playerGoing);
        System.out.println();
        FieldDisplay.displayFieldForPlayerWithoutShips(enemyPlayer);

        while(true) {
            System.out.print("Enter the coordinate to fire at (x,y): ");
            String coordinate = ConsoleUtils.readLine();

            try {
                if(!isShotCoordinateStringValid(coordinate)) {
                    throw new Field.InvalidShotException();
                }

                String[] coordinateComponents = coordinate.split(",");
                int shotX = Integer.valueOf(coordinateComponents[0]);
                int shotY = Integer.valueOf(coordinateComponents[1]);

                Ship shipHit = enemyPlayer.attemptHitAt(shotX, shotY);

                System.out.println();
                if(shipHit != null) {
                    System.out.println("That was a hit!");
                    if(shipHit.isSunk()) {
                        System.out.println("You sunk " + enemyPlayer.getName() + "'s " + shipHit.getName() + "!");
                    }
                } else {
                    System.out.println("That was a miss!");
                }

                System.out.println("Press enter to continue");
                ConsoleUtils.waitForUserToPressEnter();

                break;
            } catch(Field.InvalidShotException e) {
                System.out.println("That wasn't a valid shot!");
            }
        }
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

    private boolean isShotCoordinateStringValid(String coordinate) {
        return Pattern.matches("[0-9]+,[0-9]+", coordinate);
    }

    private void displayWinner(Player winner) {
        System.out.println(winner.getName() + " won! Congrats!\n");

        for(Player player : battleshipEngine.getPlayers()) {
            FieldDisplay.displayFieldForPlayer(player);
            System.out.println();
        }
    }
}
