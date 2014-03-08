package com.boztalay.battleship.engine;

/**
 *  Responsible for updating the field with
 *  hits and misses, setting up the ship placements,
 *  and determining when the player has lost
 */
public class Player {
    private Field field;

    private String name;

    public Player(String name, int fieldSize) {
        field = new Field(fieldSize);
        this.name = name;
    }

    public Ship attemptHitAt(int x, int y) throws Field.InvalidShotException {
        Ship shipHit = field.attemptHitAt(x, y);

        return shipHit;
    }

    public void placeShip(Ship shipToPlace) throws Field.InvalidShipPlacementException {
        field.addShip(shipToPlace);
    }

    public boolean areAllShipsSunk() {
        for(int i = 0; i < field.getNumberOfShips(); i++) {
            if(!field.getShip(i).isSunk()) {
                return false;
            }
        }

        return true;
    }

    public String getName() {
        return this.name;
    }

    public Field getField() {
        return this.field;
    }
}
