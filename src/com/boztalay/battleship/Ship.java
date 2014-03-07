package com.boztalay.battleship;

/**
 * Holds the ship's location, orientation, length, and status
 */
public class Ship {
    public enum ShipOrientation { HORIZONTAL, VERTICAL }

    private int x;
    private int y;
    private ShipOrientation orientation;
    private int length;
    private int hits;

    public Ship() {
        this.x = 0;
        this.y = 0;
        orientation = ShipOrientation.HORIZONTAL;
        this.length = 0;
        this.hits = 0;
    }

    public Ship(int x, int y, ShipOrientation orientation, int length) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.length = length;
        this.hits = 0;
    }

    public boolean isWithinFieldBounds(int fieldSize) {
        if(this.x < 0 || this.y < 0 || this.x >= fieldSize || this.y >= fieldSize) {
            return false;
        }

        int shipCoordinateToCheck = 0;
        if(this.orientation == Ship.ShipOrientation.HORIZONTAL) {
            shipCoordinateToCheck = this.x;
        } else {
            shipCoordinateToCheck = this.y;
        }

        if(shipCoordinateToCheck > (fieldSize - this.length)) {
            return false;
        }

        return true;
    }

    //This could use some cleaning
    //A ship can't intersect itself
    public boolean doesIntersectShip(Ship otherShip) {
        if(this == otherShip) {
            return false;
        }

        if(otherShip.orientation == this.orientation) {
            if(this.orientation == ShipOrientation.HORIZONTAL) {
                return (otherShip.doesOccupySpaceAt(this.x, this.y) || otherShip.doesOccupySpaceAt(this.x + this.length - 1, this.y));
            } else {
                return (otherShip.doesOccupySpaceAt(this.x, this.y) || otherShip.doesOccupySpaceAt(this.x, this.y + this.length - 1));
            }
        } else {
            if(this.orientation == ShipOrientation.HORIZONTAL) {
                if(otherShip.x >= this.x && otherShip.x < (this.x + this.length)) {
                    return (this.y >= otherShip.y && this.y < (otherShip.y + otherShip.length));
                }
            } else {
                if(this.x >= otherShip.x && this.x < (otherShip.x + otherShip.length)) {
                    return (otherShip.y >= this.y && otherShip.y < (this.y + this.length));
                }
            }
        }

        return false;
    }

    public boolean doesOccupySpaceAt(int x, int y) {
        if(orientation == ShipOrientation.HORIZONTAL && this.y == y) {
            return (x >= this.x && x < (this.x + this.length));
        } else if(this.x == x) {
            return (y >= this.y && y < (this.y + this.length));
        }

        return false;
    }

    public void hit() {
        this.hits++;
    }

    public boolean isSunk() {
        return this.hits >= this.length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ShipOrientation getOrientation() {
        return orientation;
    }

    public int getLength() {
        return length;
    }

    public int getHits() {
        return hits;
    }
}
