package com.densev.chess.game.moves;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Cell;
import com.densev.chess.game.board.Piece;
import com.densev.chess.game.events.CheckmateEvent;
import com.densev.chess.game.events.Dispatcher;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Move class, selected for each piece to be moved. Subclasses define how pieces are moved.
 * <p>
 * Created on: 10/23/18
 */
public abstract class Move {

    private static final Logger log = LoggerFactory.getLogger(Move.class);

    Board board;

    public Move(Board board) {
        this.board = board;
    }

    /**
     * Checks that piece at from position can be move to to position
     *
     * @param from - position of piece to move
     * @param to   - position to move to
     * @return - true if piece at from position can be moved to to position
     */
    public boolean canMoveTo(Position from, Position to) {
        List<Position> positions = getAvailableMovePositions(from);
        return CollectionUtils.isNotEmpty(positions) && positions.contains(to);
    }

    /**
     * Moves the piece at from position to to position, captures a piece if to position contains a piece of the
     * opposing team, dispatches {@link CheckmateEvent} if a piece at to position is the opposing king
     *
     * @param from - position of the piece to move
     * @param to   - position to move to
     */
    public void move(Position from, Position to) {
        Cell cellAtTo = fileAt(to.getX(), to.getY());
        Cell cellAtFrom = fileAt(from.getX(), from.getY());

        if (cellAtTo.isNotEmpty()) {
            log.info("Captured: {}", cellAtTo);
            if (Piece.KING.equals(cellAtTo.getPiece())) {
                log.info("{} king captured! Checkmate.", cellAtTo.getColor());
                Dispatcher.INSTANCE.handleEvent(new CheckmateEvent());
            }
        }
        updatePositions(cellAtFrom, from, to);
    }

    /**
     * Updates position of the cell on the board
     *
     * @param cellAtFrom - the cell that moved
     * @param from       - the initial cell position
     * @param to         - the position cell moved to
     */
    private void updatePositions(Cell cellAtFrom, Position from, Position to) {
        // clear previous position
        this.board.setCellAt(Cell.empty(), from.getX(), from.getY());
        // set file at new position to file that is being moved
        this.board.setCellAt(cellAtFrom, to.getX(), to.getY());
    }

    /**
     * Wrapper for {@link Board}.cellAt method. Returns the cell at x,y
     *
     * @param x - x position on the board
     * @param y - y position on the board
     * @return - the cell at x, y
     */
    Cell fileAt(int x, int y) {
        return this.board.cellAt(x, y);
    }

    /**
     * Returns available move positions of the piece at from position
     *
     * @param from - the position of the piece to move
     * @return - list of positions the piece at from can move to
     */
    public abstract List<Position> getAvailableMovePositions(Position from);
}
