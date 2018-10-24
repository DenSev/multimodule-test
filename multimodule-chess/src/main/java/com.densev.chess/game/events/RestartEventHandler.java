package com.densev.chess.game.events;

import com.densev.chess.game.Game;

/**
 * Handles restarting the game, resets checkmate and calls playGame
 *
 * Created on: 10/24/18
 */
public class RestartEventHandler implements Handler<RestartEvent> {

    @Override
    public void handle(RestartEvent restartEvent) {
        Game.INSTANCE.setCheckmate(true);
        Game.INSTANCE.playGame();
    }
}
