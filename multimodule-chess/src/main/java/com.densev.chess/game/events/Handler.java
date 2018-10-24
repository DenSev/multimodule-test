package com.densev.chess.game.events;

/**
 * Handler interface, all handlers implement it
 *
 * @param <T> - handled event type
 */
public interface Handler<T extends Event> {

    void handle(T t);

}
