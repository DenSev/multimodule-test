package com.densev.chess.util;

import com.densev.chess.game.events.Dispatcher;
import com.densev.chess.game.events.RestartEvent;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Scanner;

/**
 * Command line input reader
 * <p>
 * Created on: 10/23/18
 */
public class Input {

    private static final Logger log = LoggerFactory.getLogger(Input.class);
    private static final String EXIT_LINE = "exit";
    private static final String RESTART_LINE = "restart";
    private static final String HELP_LINE = "help";
    private static final Scanner SCANNER = new Scanner(System.in);

    private static final Map<String, InputHandler> inputHandlers = ImmutableMap.<String, InputHandler>builder()
        .put(EXIT_LINE, () -> System.exit(0))
        .put(RESTART_LINE, () -> Dispatcher.INSTANCE.handleEvent(new RestartEvent()))
        .put(HELP_LINE, () -> log.info("Available commands: exit, restart, help"))
        .build();

    /**
     * Returns a string line and handles system inputs
     *
     * @return - a line
     */
    public static String getLine() {
        String input = SCANNER.nextLine();
        if (handleSystemInputs(input)) {
            return getLine();
        }
        return input;
    }

    /**
     * Returns an integer and handles system inputs, additionally handles number format exceptions
     *
     * @return - an integer
     */
    public static Integer getInteger() {
        String input = SCANNER.nextLine();
        if (handleSystemInputs(input)) {
            return getInteger();
        }
        try {
            return Integer.valueOf(input);
        } catch (NumberFormatException e) {
            log.debug(e.getMessage(), e);
            log.error("Not a valid number. Please enter a number that is correct.");
            return getInteger();
        }

    }

    /**
     * Handles system inputs
     *
     * @param input - input line
     * @return - returns true if system input has been handled, and false if not
     */
    private static boolean handleSystemInputs(String input) {
        InputHandler inputHandler = inputHandlers.get(input);
        if (inputHandler != null) {
            inputHandler.handle();
            return true;
        }
        return false;
    }

    public static void shutdown() {
        SCANNER.close();
    }

}
