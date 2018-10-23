package com.densev.chess.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Created on: 10/23/18
 */
public class Input {

    private static final Logger LOG = LoggerFactory.getLogger(Input.class);
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String getLine() {
        String input = SCANNER.nextLine();
        handleSystem(input);
        return input;
    }

    public static Integer getNumber() {
        String number = SCANNER.nextLine();
        handleSystem(number);
        try {
            return Integer.valueOf(number);
        } catch (NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            LOG.error("Not a valid number. Please enter a number that is correct.");
            return getNumber();
        }
    }

    private static void handleSystem(String input) {
        if ("exit".equals(input)) {
            System.exit(0);
        }
    }

    public static void shutdown(){
        SCANNER.close();
    }

}
