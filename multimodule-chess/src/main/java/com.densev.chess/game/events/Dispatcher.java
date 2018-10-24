package com.densev.chess.game.events;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Event dispatcher, maps event types to handlers
 */
public enum Dispatcher {

    INSTANCE;

    public Map<Class<? extends Event>, Handler> eventHandlers = ImmutableMap
        .<Class<? extends Event>, Handler>builder()
        .put(CheckmateEvent.class, new CheckmateEventHandler())
        .put(PawnPromoteEvent.class, new PawnPromoteEventHandler())
        .put(RandomPawnPromoteEvent.class, new RandomPawnPromoteEventHandler())
        .put(RestartEvent.class, new RestartEventHandler())
        .build();

    /**
     * Passes the event in param to appropriate handler
     *
     * @param event - event to handle
     * @param <T>   - event type
     */
    public <T extends Event> void handleEvent(T event) {
        eventHandlers.get(event.getClass()).handle(event);
    }
}
