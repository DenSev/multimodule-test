package com.densev.chess.game.events;

import com.densev.chess.game.board.Cell;

/**
 * Created on: 10/24/18
 */
public class RandomPawnPromoteEvent extends PawnPromoteEvent {

    public RandomPawnPromoteEvent(Cell pawn) {
        super(pawn);
    }
}
