package com.densev.chess.game.moves;

import com.densev.chess.Utils;
import com.densev.chess.game.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * King can move exactly one square horizontally, vertically, or diagonally.
 * At most once in every game, each king is allowed to make a special move, known as castling.
 * <p>
 * Created on: 10/23/18
 */
public class KingMove extends Move {

    public KingMove(Board board) {
        super(board);
    }

    @Override
    public List<Position> getAvailableMovePositions(Position from) {
        List<Position> availablePositions = new ArrayList<>();
        int x = from.getX();
        int y = from.getY();

        addIfAvailable(availablePositions, x + 1, y + 1);
        addIfAvailable(availablePositions, x + 1, y);
        addIfAvailable(availablePositions, x + 1, y - 1);

        addIfAvailable(availablePositions, x, y + 1);
        addIfAvailable(availablePositions, x, y - 1);

        addIfAvailable(availablePositions, x - 1, y + 1);
        addIfAvailable(availablePositions, x - 1, y);
        addIfAvailable(availablePositions, x - 1, y - 1);

        return availablePositions;
    }

    private Position available(int x, int y) {
        if (Utils.isInBounds(x, y) && board.fileAt(x, y).isEmpty()) {
            return new Position(x, y);
        }
        return null;
    }

    private void addIfAvailable(List<Position> positions, int x, int y) {
        Position position = available(x, y);
        if (position != null) {
            positions.add(position);
        }
    }
}
