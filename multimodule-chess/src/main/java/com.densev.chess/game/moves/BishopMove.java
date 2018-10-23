package com.densev.chess.game.moves;

import com.densev.chess.util.BoardUtils;
import com.densev.chess.game.board.Board;

import java.util.List;

/**
 * Bishop can move any number of vacant squares in any diagonal direction.
 * <p>
 * Created on: 10/23/18
 */
public class BishopMove extends Move {

    public BishopMove(Board board) {
        super(board);
    }

    @Override
    public List<Position> getAvailableMovePositions(Position from) {
        return BoardUtils.getDiagonalMovePositions(from, this.board);
    }
}
