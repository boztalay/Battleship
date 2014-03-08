package com.boztalay.battleship.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUtils {
    private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public static String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public static void waitForUserToPressEnter() {
        try {
            input.readLine();
        } catch (IOException e) {
            //Do nothing
        }
    }

    public static void clearConsole() {
        //A little hacky, but there isn't a nice Java solution
        for(int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
}
