package com.densev.chess.game.events;

public class CheckmateEventHandler implements Handler<CheckmateEvent> {

    @Override
    public boolean handle(CheckmateEvent checkmateEvent) {
        return false;
    }
}
