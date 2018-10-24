package com.densev.chess.game.events;

import com.densev.chess.game.board.Cell;

/**
 * Pawn promotion event dispatched from {@link com.densev.chess.players.RandomAIController}
 * Extends pawn promotion event for abstraction
 * <p>
 * Created on: 10/24/18
 */
public class RandomPawnPromoteEvent extends PawnPromoteEvent {

    public RandomPawnPromoteEvent(Cell pawn) {
        super(pawn);
    }
}
