package com.boztalay.battleship;

/**
 * Holds the ship's location, orientation, length, and status
 */
public class Ship {
    public enum ShipOrientation { HORIZONTAL, VERTICAL }

    public int x;
    public int y;
    public ShipOrientation orientation;
    public int length;
    public int hits;

    public Ship() {
        this.x = 0;
        this.y = 0;
        orientation = ShipOrientation.HORIZONTAL;
        this.length = 0;
        this.hits = 0;
    }

    public Ship(int x, int y, ShipOrientation orientation, int length, int hits) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.length = length;
        this.hits = hits;
    }
}
