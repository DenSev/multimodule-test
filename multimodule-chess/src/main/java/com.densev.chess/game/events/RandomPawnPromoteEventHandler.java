package com.densev.chess.game.events;

import com.densev.chess.game.board.Cell;
import com.densev.chess.game.board.Piece;
import com.densev.chess.players.RandomAIPlayer;
import com.densev.chess.util.RandomBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles pawn promotion event dispatched from {@link RandomAIPlayer}
 * Randomly selects a piece to promote to.
 * <p>
 * Created on: 10/24/18
 */
public class RandomPawnPromoteEventHandler implements Handler<RandomPawnPromoteEvent> {

    private static final Logger log = LoggerFactory.getLogger(RandomPawnPromoteEventHandler.class);
    private static final Piece[] AVAILABLE_PIECES = new Piece[]{Piece.QUEEN, Piece.BISHOP, Piece.ROOK, Piece.KNIGHT};

    @Override
    public void handle(RandomPawnPromoteEvent randomPawnPromoteEvent) {
        Cell pawn = randomPawnPromoteEvent.getPawn();
        int randomPieceNum = RandomBag.getInt(AVAILABLE_PIECES.length);
        Piece randomPiece = AVAILABLE_PIECES[randomPieceNum];
        pawn.setPiece(randomPiece);
        log.info("Pawn has been randomly promoted to {}", randomPiece);
    }
}
