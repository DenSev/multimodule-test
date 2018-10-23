package com.densev.chess.game.events;

public interface Handler<T extends Event> {

    boolean handle(T t);

}
