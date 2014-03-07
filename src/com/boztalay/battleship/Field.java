package com.boztalay.battleship;

import java.util.ArrayList;

/**
 * Responsible for keeping track of ship placements,
 * along with hits and misses
 */
public class Field {
    public enum SquareType {EMPTY, MISS, HIT}

    private SquareType[][] field;
    private ArrayList<Ship> ships;

    public Field(int fieldSize) {
        field = new SquareType[fieldSize][fieldSize];
        ships = new ArrayList<Ship>();
    }

    public void addShip(Ship shipToAdd) throws InvalidShipPlacementException {
        if(!isShipPlacementValid(shipToAdd)) {
            throw new InvalidShipPlacementException();
        }

        ships.add(shipToAdd);
    }

    public boolean isShipPlacementValid(Ship shipToCheck) {
        if(shipToCheck.x < 0 || shipToCheck.y < 0 || shipToCheck.x >= field.length || shipToCheck.y >= field.length) {
            return false;
        }

        int shipCoordinateToCheck = 0;
        if(shipToCheck.orientation == Ship.ShipOrientation.HORIZONTAL) {
            shipCoordinateToCheck = shipToCheck.x;
        } else {
            shipCoordinateToCheck = shipToCheck.y;
        }

        if(shipCoordinateToCheck > (field.length - shipToCheck.length)) {
            return false;
        }

        return true;
    }

    public class InvalidShipPlacementException extends Exception {
    }
}
