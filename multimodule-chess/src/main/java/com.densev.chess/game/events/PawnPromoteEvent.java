package com.densev.chess.game.events;

import com.densev.chess.game.board.Cell;

/**
 * Created on: 10/24/18
 */
public class PawnPromoteEvent implements Event{

    Cell pawn;

    public PawnPromoteEvent(Cell pawn) {
        this.pawn = pawn;
    }

    public Cell getPawn() {
        return pawn;
    }
}
