package com.densev.chess.game.moves;

import com.densev.chess.util.BoardUtils;
import com.densev.chess.game.board.Board;

import java.util.List;

/**
 * Queen can move any number of vacant squares diagonally, horizontally, or vertically.
 * <p>
 * Created on: 10/23/18
 */
public class QueenMove extends Move {

    public QueenMove(Board board) {
        super(board);
    }

    @Override
    public List<Position> getAvailableMovePositions(Position from) {
        List<Position> availablePositions = BoardUtils.getDiagonalMovePositions(from, board);
        availablePositions.addAll(BoardUtils.getHorizontalMovePositions(from, board));
        availablePositions.addAll(BoardUtils.getVerticalMovePositions(from, board));

        return availablePositions;
    }
}
