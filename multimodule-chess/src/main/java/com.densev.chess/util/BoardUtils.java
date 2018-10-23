package com.densev.chess.util;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Cell;
import com.densev.chess.game.moves.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on: 10/23/18
 */
public final class BoardUtils {

    private BoardUtils() {
    }

    public static boolean isInBounds(int x, int y) {
        return x >= 0 && x < Board.BOARD_SIZE && y >= 0 && y < Board.BOARD_SIZE;
    }

    private static boolean breakOrAddAndContinue(Board board, List<Position> positions, Cell cellMoved, int x, int y) {
        //break when out of bounds
        if (!isInBounds(x, y)) {
            return true;
        }
        Cell cellAt = board.fileAt(x, y);
        // should break if we find piece of the same team
        if (cellAt.isNotEmpty() && cellAt.isNotOpposing(cellMoved)) {
            return true;
        }
        positions.add(new Position(x, y));
        // break if position added is of piece of opposing team
        if (cellAt.isOpposing(cellMoved)) {
            return true;
        }
        return false;
    }

    public static List<Position> getDiagonalMovePositions(Position from, Board board) {
        final int x = from.getX();
        final int y = from.getY();
        final Cell cellMoved = board.fileAt(x, y);
        List<Position> availablePositions = new ArrayList<>();

        // upper right
        for (int i = 1; i <= Board.BOARD_SIZE; i++) {
            if (breakOrAddAndContinue(board, availablePositions, cellMoved, x + i, y + i)) {
                break;
            }
        }
        //upper left
        for (int i = 1; i <= Board.BOARD_SIZE; i++) {
            if (breakOrAddAndContinue(board, availablePositions, cellMoved, x - i, y + i)) {
                break;
            }
        }
        //lower right
        for (int i = 1; i <= Board.BOARD_SIZE; i++) {
            if (breakOrAddAndContinue(board, availablePositions, cellMoved, x + i, y - i)) {
                break;
            }
        }
        //lower left
        for (int i = 1; i <= Board.BOARD_SIZE; i++) {
            if (breakOrAddAndContinue(board, availablePositions, cellMoved, x - i, y - i)) {
                break;
            }
        }

        return availablePositions;
    }

    public static List<Position> getHorizontalMovePositions(Position from, Board board) {
        final int y = from.getY();
        final Cell cellMoved = board.fileAt(from.getX(), y);
        List<Position> availablePositions = new ArrayList<>();
        for (int i = from.getX() + 1; i < Board.BOARD_SIZE; i++) {
            if (breakOrAddAndContinue(board, availablePositions, cellMoved, i, y)) {
                break;
            }
        }

        for (int i = from.getX() - 1; i >= 0; i--) {
            if (breakOrAddAndContinue(board, availablePositions, cellMoved, i, y)) {
                break;
            }
        }

        return availablePositions;
    }

    public static List<Position> getVerticalMovePositions(Position from, Board board) {
        final int x = from.getX();
        final Cell cellMoved = board.fileAt(x, from.getY());
        List<Position> availablePositions = new ArrayList<>();
        for (int i = from.getY() + 1; i < Board.BOARD_SIZE; i++) {
            if (breakOrAddAndContinue(board, availablePositions, cellMoved, x, i)) {
                break;
            }
        }

        for (int i = from.getY() - 1; i >= 0; i--) {
            if (breakOrAddAndContinue(board, availablePositions, cellMoved, x, i)) {
                break;
            }
        }

        return availablePositions;
    }

}
