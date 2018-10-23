package com.densev.chess.game.moves;

import com.densev.chess.util.BoardUtils;
import com.densev.chess.game.board.Board;

import java.util.List;

/**
 * Rook can move any number of vacant squares vertically or horizontally. It also is moved while castling.
 * <p>
 * Created on: 10/23/18
 */
public class RookMove extends Move {

    public RookMove(Board board) {
        super(board);
    }

    @Override
    public List<Position> getAvailableMovePositions(Position from) {
        List<Position> availablePositions = BoardUtils.getHorizontalMovePositions(from, this.board);
        availablePositions.addAll(BoardUtils.getVerticalMovePositions(from, this.board));

        return availablePositions;
    }
}
