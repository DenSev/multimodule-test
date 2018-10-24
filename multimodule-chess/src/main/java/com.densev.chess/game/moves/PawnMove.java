package com.densev.chess.game.moves;

import com.densev.chess.util.BoardUtils;
import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Cell;
import com.densev.chess.game.board.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Pawns can move forward one square, if that square is unoccupied.
 * If it has not yet moved, the pawn has the option of moving two
 * squares forward provided both squares in front of the pawn are unoccupied.
 * A pawn cannot move backward. Pawns are the only pieces that capture
 * differently from how they move. They can capture an enemy piece on
 * either of the two spaces adjacent to the space in front of them
 * (i.e., the two squares diagonally in front of them) but cannot move
 * to these spaces if they are vacant. The pawn is also involved in the
 * two special moves, en passant and promotion.
 * <p>
 * Created on: 10/23/18
 */
public class PawnMove extends Move {

    public PawnMove(Board board) {
        super(board);
    }

    /**
     * Checks pawn orientation, BLACK pawns are always down, WHITE pawns are always up
     *
     * @param cell - cell to check
     * @return - true if pawn is black, false if pawn is white
     */
    private boolean orientationIsDown(Cell cell) {
        return Color.BLACK.equals(cell.getColor());
    }

    /**
     * Checks if pawn can capture any pieces, since pawn is the only piece that captures differently to moving
     *
     * @param orientationIsDown - pawn orientation
     * @param pawn - the pawn cell
     * @param x - x of the pawn position
     * @param y - y of the pawn position
     * @return - list of capturable positions
     */
    private List<Position> getCapturable(final boolean orientationIsDown, Cell pawn, int x, int y) {
        List<Position> capturablePositions = new ArrayList<>();
        if (orientationIsDown) {
            if (BoardUtils.isInBounds(x - 1, y - 1)
                && fileAt(x - 1, y - 1).isOpposing(pawn)) {

                capturablePositions.add(new Position(x - 1, y - 1));
            }

            if (BoardUtils.isInBounds(x + 1, y - 1)
                && fileAt(x + 1, y - 1).isOpposing(pawn)) {

                capturablePositions.add(new Position(x + 1, y - 1));
            }

            return capturablePositions;
        }

        if (BoardUtils.isInBounds(x - 1, y + 1)
            && fileAt(x - 1, y + 1).isOpposing(pawn)) {

            capturablePositions.add(new Position(x - 1, y + 1));
        }

        if (BoardUtils.isInBounds(x + 1, y + 1)
            && fileAt(x + 1, y + 1).isOpposing(pawn)) {

            capturablePositions.add(new Position(x + 1, y + 1));
        }

        return capturablePositions;
    }

    /**
     * Returns a list of available move positions of the pawn moving
     * Position calculation differs based on pawn orientation and whether
     * there are any pieces a pawn can capture or not.
     *
     * @param from - the position of the piece to move
     * @return - list of available move positions
     */
    @Override
    public List<Position> getAvailableMovePositions(Position from) {
        int x = from.getX();
        int y = from.getY();

        //the pawn has the option of moving two
        //squares forward provided both squares in front of the pawn are unoccupied
        boolean hasNotMovedYet = y == 1 || y == 6;

        Cell pawn = fileAt(x, y);
        boolean orientationDown = orientationIsDown(pawn);

        List<Position> availablePositions = getCapturable(orientationDown, pawn, x, y);

        if (orientationDown) {
            if (BoardUtils.isInBounds(x, y - 1) && board.cellAt(x, y - 1).isEmpty()) {
                availablePositions.add(new Position(x, y - 1));

                if (hasNotMovedYet && BoardUtils.isInBounds(x, y - 2) && board.cellAt(x, y - 2).isEmpty()) {
                    availablePositions.add(new Position(x, y - 2));
                }
            }
            return availablePositions;
        }

        if (BoardUtils.isInBounds(x, y + 1) && board.cellAt(x, y + 1).isEmpty()) {
            availablePositions.add(new Position(x, y + 1));

            if (hasNotMovedYet && BoardUtils.isInBounds(x, y + 2) && board.cellAt(x, y + 2).isEmpty()) {
                availablePositions.add(new Position(x, y + 2));
            }
        }

        return availablePositions;
    }


}
