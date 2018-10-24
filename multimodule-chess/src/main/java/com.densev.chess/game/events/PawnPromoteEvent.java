package com.densev.chess.game.events;

import com.densev.chess.game.board.Cell;
import com.densev.chess.players.CommandLinePlayer;

/**
 * Pawn promotion event, dispatched from {@link CommandLinePlayer}
 * Contains promoted pawn cell.
 * <p>
 * Created on: 10/24/18
 */
public class PawnPromoteEvent implements Event {

    /**
     * Pawn promoted
     */
    private Cell pawn;

    public PawnPromoteEvent(Cell pawn) {
        this.pawn = pawn;
    }

    public Cell getPawn() {
        return pawn;
    }
}
