package com.densev.chess.util;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Cell;
import com.densev.chess.game.moves.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Several util methods for board and piece movement
 *
 * Created on: 10/23/18
 */
public final class BoardUtils {

    private BoardUtils() {
    }

    /**
     * Checks that provided coordinates are in bounds
     *
     * @param x - x to check
     * @param y - y to check
     * @return - true if both coordinates are in bounds, false otherwise
     */
    public static boolean isInBounds(int x, int y) {
        return x >= 0 && x < Board.BOARD_SIZE && y >= 0 && y < Board.BOARD_SIZE;
    }

    /**
     * Checks that the position at x,y can be added to list of available movement positions, and returns true
     * if break should be called for loop and false if loop should continue
     *
     * @param board - game board
     * @param positions - list of available movement positions
     * @param cellMoved - cell that is being moved, needed to check if position is occupied by opposing piece
     * @param x - x of position to potentially add
     * @param y - y of position to potentially add
     * @return - true if break should be called for loop and false if loop should continue
     */
    private static boolean breakOrAddAndContinue(Board board, List<Position> positions, Cell cellMoved, int x, int y) {
        //break when out of bounds
        if (!isInBounds(x, y)) {
            return true;
        }
        Cell cellAt = board.cellAt(x, y);
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

    /**
     * Returns movement positions for a piece that can move diagonally: queen and bishop
     *
     * @param from - the piece's initial position
     * @param board - the game board
     * @return - a list of available movement positions
     */
    public static List<Position> getDiagonalMovePositions(Position from, Board board) {
        final int x = from.getX();
        final int y = from.getY();
        final Cell cellMoved = board.cellAt(x, y);
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

    /**
     * Returns a list of available movement positions for pieces that can move horizontally
     * Pieces that can move horizontally: Queen, Rook
     *
     * @param from - the piece's initial position
     * @param board - the game board
     * @return - a list of available movement positions
     */
    public static List<Position> getHorizontalMovePositions(Position from, Board board) {
        final int y = from.getY();
        final Cell cellMoved = board.cellAt(from.getX(), y);
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

    /**
     * Returns a list of available movement positions for pieces that can move vertially
     * Pieces that can move vertically: Queen, Rook
     *
     * @param from - the piece's initial position
     * @param board - the game board
     * @return - a list of available positions
     */
    public static List<Position> getVerticalMovePositions(Position from, Board board) {
        final int x = from.getX();
        final Cell cellMoved = board.cellAt(x, from.getY());
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
