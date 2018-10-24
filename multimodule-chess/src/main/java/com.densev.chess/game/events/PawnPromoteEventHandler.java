package com.densev.chess.game.events;

import com.densev.chess.game.board.Cell;
import com.densev.chess.game.board.Piece;
import com.densev.chess.util.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on: 10/24/18
 */
public class PawnPromoteEventHandler implements Handler<PawnPromoteEvent>{

    private static final Logger log = LoggerFactory.getLogger(PawnPromoteEventHandler.class);

    @Override
    public boolean handle(PawnPromoteEvent pawnPromoteEvent) {
        Cell pawn = pawnPromoteEvent.getPawn();

        log.info("Your pawn can be promoted. Choose a piece to convert to.\n" +
            "Available pieces: queen, bishop, rook, knight.\n" +
            "Enter piece name:");

        Piece newPiece = getPiece();
        pawn.setPiece(newPiece);

        return true;
    }

    private Piece getPiece(){
        String pieceName = Input.getLine();
        try {
            return Piece.valueOf(pieceName.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage(), e);
            log.error("No such piece exists. Please enter a correct name: queen, bishop, rook, knight.");
            return getPiece();
        }
    }
}
