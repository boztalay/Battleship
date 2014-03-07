package com.boztalay.battleship;

import java.util.ArrayList;

/**
 * Responsible for keeping track of ship placements,
 * along with hits and misses
 */
public class Field {
    public enum SpaceType {EMPTY, MISS, HIT}

    private SpaceType[][] field;
    private ArrayList<Ship> ships;

    public Field(int fieldSize) {
        field = new SpaceType[fieldSize][fieldSize];
        ships = new ArrayList<Ship>();
    }

    public void addShip(Ship shipToAdd) throws InvalidShipPlacementException {
        if(!isShipPlacementValid(shipToAdd)) {
            throw new InvalidShipPlacementException();
        }

        ships.add(shipToAdd);
    }

    private boolean isShipPlacementValid(Ship shipToCheck) {
        if(!shipToCheck.isWithinFieldBounds(field.length)) {
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

    public SpaceType attemptHitAt(int x, int y) throws ShotAtNonemptySpaceException {
        if(field[x][y] == SpaceType.EMPTY) {
            throw new ShotAtNonemptySpaceException();
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

    public class InvalidShipPlacementException extends Exception {}
    public class ShotAtNonemptySpaceException extends Exception {}
}