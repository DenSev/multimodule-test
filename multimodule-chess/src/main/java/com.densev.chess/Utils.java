package com.densev.chess;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.File;
import com.densev.chess.game.moves.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on: 10/23/18
 */
public final class Utils {

    private Utils() {
    }

    public static boolean isInBounds(int x, int y) {
        return x >= 0 && x < Board.BOARD_SIZE && y >= 0 && y < Board.BOARD_SIZE;
    }

    private static boolean breakOrAddAndContinue(Board board, List<Position> positions, File fileMoved, int x, int y) {
        if(!isInBounds(x, y)) {
            return false;
        }
        File fileAt = board.fileAt(x, y);
        // should not break if we find piece of the same team, but should not add position
        if (fileAt.isNotEmpty() && fileAt.isNotOpposing(fileMoved)) {
            return false;
        }
        positions.add(new Position(x, y));
        // break if position added is of piece of opposing team
        if (fileAt.isOpposing(fileMoved)) {
            return true;
        }
        return false;
    }

    public static List<Position> getDiagonalMovePositions(Position from, Board board) {
        final int x = from.getX();
        final int y = from.getY();
        final File fileMoved = board.fileAt(x, y);
        List<Position> availablePositions = new ArrayList<>();

        // upper right
        for (int i = 1; i <= Board.BOARD_SIZE / 2; i++) {
            if (x + i > Board.BOARD_SIZE || y + i > Board.BOARD_SIZE) {
                break;
            }
            if (breakOrAddAndContinue(board, availablePositions, fileMoved, x + i, y + 1)) {
                break;
            }
        }
        //upper left
        for (int i = 1; i <= Board.BOARD_SIZE / 2; i++) {
            if (x - i < 0 || y + i > Board.BOARD_SIZE) {
                break;
            }
            if (breakOrAddAndContinue(board, availablePositions, fileMoved, x - i, y + 1)) {
                break;
            }
        }
        //lower right
        for (int i = 1; i <= Board.BOARD_SIZE / 2; i++) {
            if (x + i > Board.BOARD_SIZE || y - i < 0) {
                break;
            }
            if (breakOrAddAndContinue(board, availablePositions, fileMoved, x + i, y - 1)) {
                break;
            }
        }
        //lower left
        for (int i = 1; i <= Board.BOARD_SIZE / 2; i++) {
            if (x - i > 0 || y - i < 0) {
                break;
            }
            if (breakOrAddAndContinue(board, availablePositions, fileMoved, x - i, y - 1)) {
                break;
            }
        }

        return availablePositions;
    }

    public static List<Position> getHorizontalMovePositions(Position from, Board board) {
        final int y = from.getY();
        final File fileMoved = board.fileAt(from.getX(), y);
        List<Position> availablePositions = new ArrayList<>();
        for (int i = from.getX() + 1; i < Board.BOARD_SIZE; i++) {
            if (breakOrAddAndContinue(board, availablePositions, fileMoved, i, y)) {
                break;
            }
        }

        for (int i = from.getX() - 1; i >= 0; i--) {
            if (breakOrAddAndContinue(board, availablePositions, fileMoved, i, y)) {
                break;
            }
        }

        return availablePositions;
    }

    public static List<Position> getVerticalMovePositions(Position from, Board board) {
        final int x = from.getX();
        final File fileMoved = board.fileAt(x, from.getY());
        List<Position> availablePositions = new ArrayList<>();
        for (int i = from.getY() + 1; i < Board.BOARD_SIZE; i++) {
            if (breakOrAddAndContinue(board, availablePositions, fileMoved, x, i)) {
                break;
            }
        }

        for (int i = from.getY() - 1; i >= 0; i--) {
            if (breakOrAddAndContinue(board, availablePositions, fileMoved, x, i)) {
                break;
            }
        }

        return availablePositions;
    }

}
