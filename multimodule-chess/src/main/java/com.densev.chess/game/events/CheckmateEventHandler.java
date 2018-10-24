package com.densev.chess.game.events;

import com.densev.chess.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles {@link CheckmateEvent}, sets Game param checkmate to true
 */
public class CheckmateEventHandler implements Handler<CheckmateEvent> {

    private static final Logger log = LoggerFactory.getLogger(CheckmateEventHandler.class);

    @Override
    public void handle(CheckmateEvent checkmateEvent) {
        Game.INSTANCE.setCheckmate(true);
    }
}
