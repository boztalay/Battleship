package com.boztalay.battleship;

public class Main {

    public static void main(String[] args) {
        Battleship battleship = new Battleship();

        try {
            battleship.playBattleship();
        } catch(Exception e) {
            System.out.println("Something went terribly wrong!");
            System.out.print(e);
        }
    }
}
