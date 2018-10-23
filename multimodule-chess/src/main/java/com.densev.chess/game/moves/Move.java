package com.densev.chess.game.moves;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.File;
import com.densev.chess.game.board.Piece;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created on: 10/23/18
 */
public abstract class Move {

    private static final Logger log = LoggerFactory.getLogger(Move.class);

    Board board;

    public Move(Board board) {
        this.board = board;
    }

    public boolean canMoveTo(Position from, Position to) {
        List<Position> positions = getAvailableMovePositions(from);
        if (CollectionUtils.isNotEmpty(positions) && positions.contains(to)) {
            return true;
        }
        return false;
    }

    public boolean move(Position from, Position to) {
        File fileAtTo = fileAt(to.getX(), to.getY());
        File fileAtFrom = fileAt(from.getX(), from.getY());

        if (fileAtTo.isNotEmpty()) {
            if (Piece.KING.equals(fileAtTo.getPiece())) {
                log.info("{} king captured! Checkmate.", fileAtTo.getColor());

                updatePositions(fileAtFrom, from, to);
                return true;
            }
            log.info("Captured: {}", fileAtTo);
        }

        updatePositions(fileAtFrom, from, to);
        return false;
    }

    private void updatePositions(File fileAtFrom, Position from, Position to) {
        // clear previous position
        this.board.setFileAt(File.empty(), from.getX(), from.getY());
        // set file at new position to file that is being moved
        this.board.setFileAt(fileAtFrom, to.getX(), to.getY());
    }

    File fileAt(int x, int y) {
        return this.board.fileAt(x, y);
    }

    public abstract List<Position> getAvailableMovePositions(Position from);
}
