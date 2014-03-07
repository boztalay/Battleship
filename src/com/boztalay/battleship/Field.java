package com.boztalay.battleship;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Responsible for keeping track of ship placements,
 * along with hits and misses
 */
public class Field {
    public enum SpaceType {
        EMPTY('.'),
        MISS('M'),
        HIT('H');

        public final char displayChar;

        SpaceType(char displayChar) {
            this.displayChar = displayChar;
        }
    }

    private SpaceType[][] field;
    private ArrayList<Ship> ships;

    public Field(int fieldSize) {
        field = new SpaceType[fieldSize][fieldSize];
        for(SpaceType[] fieldRow : field) {
            Arrays.fill(fieldRow, SpaceType.EMPTY);
        }

        ships = new ArrayList<Ship>();
    }

    public void addShip(Ship shipToAdd) throws InvalidShipPlacementException {
        if(!isShipPlacementValid(shipToAdd)) {
            throw new InvalidShipPlacementException();
        }

        ships.add(shipToAdd);
    }

    private boolean isShipPlacementValid(Ship shipToCheck) {
        if(!shipToCheck.isWithinFieldBounds(getSize())) {
            return false;
        }

        if(doesShipIntersectExistingShips(shipToCheck)) {
            return false;
        }

        return true;
    }

    private boolean doesShipIntersectExistingShips(Ship shipToCheck) {
        for(Ship ship : ships) {
            if(shipToCheck.doesIntersectShip(ship)) {
                return true;
            }
        }

        return false;
    }

    public int getNumberOfShips() {
        return ships.size();
    }

    public Ship getShip(int shipIndex) {
        return ships.get(shipIndex);
    }

    public SpaceType attemptHitAt(int x, int y) throws InvalidShotException {
        if(field[x][y] != SpaceType.EMPTY) {
            throw new InvalidShotException();
        } else if(x < 0 || x >= getSize() || y < 0 || y >= getSize()) {
            throw new InvalidShotException();
        }

        Ship shipOccupyingSpace = getShipOccupyingSpaceAt(x, y);
        if(shipOccupyingSpace == null) {
            field[x][y] = SpaceType.MISS;
        } else {
            field[x][y] = SpaceType.HIT;
            shipOccupyingSpace.hit();
        }

        return field[x][y];
    }

    private Ship getShipOccupyingSpaceAt(int x, int y) {
        for(Ship ship : ships) {
            if(ship.doesOccupySpaceAt(x, y)) {
                return ship;
            }
        }

        return null;
    }

    public int getSize() {
        return field.length;
    }

    public SpaceType getSpaceAt(int x, int y) {
        return field[x][y];
    }

    public static class InvalidShipPlacementException extends Exception {}
    public static class InvalidShotException extends Exception {}
}
