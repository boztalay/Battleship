package com.boztalay.battleship.engine;

/**
 * Holds the ship's location, orientation, length, and status
 */
public class Ship {
    public enum ShipType {
        CARRIER("Aircraft Carrier", 5),
        BATTLESHIP("BattleshipCLI", 4),
        SUBMARINE("Submarine", 3),
        CRUISER("Cruiser", 3),
        DESTROYER("Destroyer", 2);

        public final String name;
        public final int length;

        ShipType(String name, int length) {
            this.name = name;
            this.length = length;
        }
    }

    public enum ShipOrientation {
        HORIZONTAL('H'),
        VERTICAL('V');

        public char shortName;

        ShipOrientation(char shortName) {
            this.shortName = shortName;
        }
    }

    private int x;
    private int y;
    private ShipOrientation orientation;
    private ShipType type;
    private int hits;

    public Ship() {
        this.x = 0;
        this.y = 0;
        orientation = ShipOrientation.HORIZONTAL;
        this.type = ShipType.DESTROYER;
        this.hits = 0;
    }

    public Ship(int x, int y, ShipOrientation orientation, ShipType type) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.type = type;
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

        if(shipCoordinateToCheck > (fieldSize - this.getLength())) {
            return false;
        }

        return true;
    }

    //A ship can't intersect itself
    public boolean doesIntersectShip(Ship otherShip) {
        if(this == otherShip) {
            return false;
        }

        if(otherShip.orientation == this.orientation) {
            if(this.orientation == ShipOrientation.HORIZONTAL) {
                return (otherShip.doesOccupySpaceAt(this.x, this.y) || otherShip.doesOccupySpaceAt(this.x + this.getLength() - 1, this.y));
            } else {
                return (otherShip.doesOccupySpaceAt(this.x, this.y) || otherShip.doesOccupySpaceAt(this.x, this.y + this.getLength() - 1));
            }
        } else {
            if(this.orientation == ShipOrientation.HORIZONTAL) {
                if(otherShip.x >= this.x && otherShip.x < (this.x + this.getLength())) {
                    return (this.y >= otherShip.y && this.y < (otherShip.y + otherShip.getLength()));
                }
            } else {
                if(this.x >= otherShip.x && this.x < (otherShip.x + otherShip.getLength())) {
                    return (otherShip.y >= this.y && otherShip.y < (this.y + this.getLength()));
                }
            }
        }

        return false;
    }

    public boolean doesOccupySpaceAt(int x, int y) {
        if(orientation == ShipOrientation.HORIZONTAL && this.y == y) {
            return (x >= this.x && x < (this.x + this.getLength()));
        } else if(orientation == ShipOrientation.VERTICAL && this.x == x) {
            return (y >= this.y && y < (this.y + this.getLength()));
        }

        return false;
    }

    public void hit() {
        this.hits++;
    }

    public boolean isSunk() {
        return this.hits >= this.getLength();
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
        return type.length;
    }

    public String getName() {
        return type.name;
    }

    public int getHits() {
        return hits;
    }
}
