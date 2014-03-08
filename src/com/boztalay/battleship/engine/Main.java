package com.boztalay.battleship.engine;

import com.boztalay.battleship.cli.BattleshipCLI;

public class Main {

    public static void main(String[] args) {
        BattleshipCLI battleshipCLI = new BattleshipCLI();

        try {
            battleshipCLI.playBattleship();
        } catch(Exception e) {
            System.out.println("Something went terribly wrong!");
            e.printStackTrace();
        }
    }
}
