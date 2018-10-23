package com.densev.chess.game.events;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public enum Dispatcher {

    INSTANCE;

    public Map<Class<? extends Event>, Handler> eventHandlers = ImmutableMap
            .<Class<? extends Event>, Handler>builder()
            .put(CheckmateEvent.class, new CheckmateEventHandler())
            .build();

    public <T extends Event> void handleEvent(T event) {
        eventHandlers.get(event.getClass()).handle(event);
    }
}
