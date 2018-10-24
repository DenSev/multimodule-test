package com.densev.chess.game.events;

import com.densev.chess.game.board.Cell;
import com.densev.chess.players.RandomAIPlayer;

/**
 * Pawn promotion event dispatched from {@link RandomAIPlayer}
 * Extends pawn promotion event for abstraction
 * <p>
 * Created on: 10/24/18
 */
public class RandomPawnPromoteEvent extends PawnPromoteEvent {

    public RandomPawnPromoteEvent(Cell pawn) {
        super(pawn);
    }
}
