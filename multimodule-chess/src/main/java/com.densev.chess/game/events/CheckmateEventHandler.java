package com.densev.chess.game.events;

import com.densev.chess.game.Game;
import com.densev.chess.util.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckmateEventHandler implements Handler<CheckmateEvent> {

    private static final Logger log = LoggerFactory.getLogger(CheckmateEventHandler.class);

    @Override
    public boolean handle(CheckmateEvent checkmateEvent) {
        log.info("Game is finished, play again? [Enter 'yes' to continue playing]");
        String input = Input.getLine();
        if ("yes".equals(input)) {
            Game.playGame();
        }

        return true;
    }
}
