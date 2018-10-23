package com.densev.chess.game.moves;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Cell;
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
        Cell cellAtTo = fileAt(to.getX(), to.getY());
        Cell cellAtFrom = fileAt(from.getX(), from.getY());

        if (cellAtTo.isNotEmpty()) {
            if (Piece.KING.equals(cellAtTo.getPiece())) {
                log.info("{} king captured! Checkmate.", cellAtTo.getColor());

                updatePositions(cellAtFrom, from, to);
                return true;
            }
            log.info("Captured: {}", cellAtTo);
        }

        updatePositions(cellAtFrom, from, to);
        return false;
    }

    private void updatePositions(Cell cellAtFrom, Position from, Position to) {
        // clear previous position
        this.board.setFileAt(Cell.empty(), from.getX(), from.getY());
        // set file at new position to file that is being moved
        this.board.setFileAt(cellAtFrom, to.getX(), to.getY());
    }

    Cell fileAt(int x, int y) {
        return this.board.fileAt(x, y);
    }

    public abstract List<Position> getAvailableMovePositions(Position from);
}
