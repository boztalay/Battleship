package com.boztalay.battleship.engine;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Responsible for holding all of the components of the game and
 * game state, as well as providing a clean API for configuring
 * and playing the game
 */
public class BattleshipEngine {
    public enum BattleshipEngineState {INITIALIZING, PLACING, PLAYING }

    BattleshipEngineState state;

    private int fieldSize;

    private ArrayList<Ship.ShipType> shipsPerPlayer;

    private ArrayList<Player> players;
    private Player currentPlayer;
    private Iterator<Player> playersIterator;

    private ArrayList<Player> currentEnemyPlayers;
    private Player currentEnemyPlayer;
    private Iterator<Player> enemyPlayersIterator;

    private Iterator<Ship.ShipType> shipsIterator;

    //Init

    public BattleshipEngine() {
        state = BattleshipEngineState.INITIALIZING;

        fieldSize = -1;
        shipsPerPlayer = new ArrayList<Ship.ShipType>();
        players = new ArrayList<Player>();
        currentEnemyPlayers = new ArrayList<Player>();
    }

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    public void addShipTypeToPlace(Ship.ShipType shipType) {
        shipsPerPlayer.add(shipType);
    }

    public void createPlayer(String name) throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.INITIALIZING);

        players.add(new Player(name, fieldSize));
    }

    //Placing

    public void beginPlacement() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.INITIALIZING);

        state = BattleshipEngineState.PLACING;

        playersIterator = players.iterator();
    }

    public boolean haveAllPlayersFinishedPlacing() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLACING);

        return !playersIterator.hasNext();
    }

    public void startPlacingShipsForNextPlayer() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLACING);

        currentPlayer = playersIterator.next();
        shipsIterator = shipsPerPlayer.iterator();
    }

    public Player getPlayerPlacingShips() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLACING);

        return currentPlayer;
    }

    public boolean hasPlayerFinishedPlacing() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLACING);

        return !shipsIterator.hasNext();
    }

    public Ship.ShipType getNextShipTypeToPlace() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLACING);

        return shipsIterator.next();
    }

    public void placeShipForCurrentPlayer(Ship ship) throws WrongStateException, Field.InvalidShipPlacementException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLACING);

        currentPlayer.placeShip(ship);
    }

    //Playing

    public void beginPlaying() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLACING);

        state = BattleshipEngineState.PLAYING;
    }

    public void prepareForRound() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLAYING);

        playersIterator = players.iterator();
    }

    public boolean haveAllPlayersTakenTheirTurn() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLAYING);

        return !playersIterator.hasNext();
    }

    public void startNextPlayersTurn() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLAYING);

        currentPlayer = playersIterator.next();

        currentEnemyPlayers.clear();
        for(Player player : players) {
            if(player != currentPlayer) {
                currentEnemyPlayers.add(player);
            }
        }

        enemyPlayersIterator = currentEnemyPlayers.iterator();
    }

    public Player getPlayerTakingTurn() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLAYING);

        return currentPlayer;
    }

    public boolean haveAllEnemyPlayersBeenFiredUpon() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLAYING);

        return !enemyPlayersIterator.hasNext();
    }

    public void advanceToNextEnemyPlayer() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLAYING);

        currentEnemyPlayer = enemyPlayersIterator.next();
    }

    public Player getCurrentEnemyPlayer() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLAYING);

        return currentEnemyPlayer;
    }

    public Ship fireOnCurrentEnemyPlayerAt(int x, int y) throws WrongStateException, Field.InvalidShotException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLAYING);

        Ship shipHit = currentEnemyPlayer.attemptHitAt(x, y);
        return shipHit;
    }

    public Player getWinningPlayer() throws WrongStateException {
        checkStateAndThrowExceptionIfWrong(BattleshipEngineState.PLAYING);

        Player winningPlayer = null;

        for(Player player : players) {
            if(!player.areAllShipsSunk()) {
                if(winningPlayer == null) {
                    winningPlayer = player;
                } else {
                    return null;
                }
            }
        }

        return winningPlayer;
    }

    //Etc

    public ArrayList<Player> getPlayers() {
        return players;
    }

    private void checkStateAndThrowExceptionIfWrong(BattleshipEngineState stateToCheckFor) throws WrongStateException {
        if(this.state != stateToCheckFor) {
            throw new WrongStateException();
        }
    }

    public static class WrongStateException extends Exception {}
}
