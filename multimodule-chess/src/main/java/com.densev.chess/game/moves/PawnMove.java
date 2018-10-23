package com.densev.chess.game.moves;

import com.densev.chess.Utils;
import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import com.densev.chess.game.board.File;

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

    public boolean orientationIsDown(File file) {
        return Color.BLACK.equals(file.getColor());
    }

    public List<Position> getCapturable(final boolean orientationIsDown, File pawn, int x, int y) {
        List<Position> capturablePositions = new ArrayList<>();
        if (orientationIsDown) {
            if (Utils.isInBounds(x - 1, y - 1)
                && fileAt(x - 1, y - 1).isOpposing(pawn)) {

                capturablePositions.add(new Position(x - 1, y - 1));
            }

            if (Utils.isInBounds(x + 1, y - 1)
                && fileAt(x + 1, y - 1).isOpposing(pawn)) {

                capturablePositions.add(new Position(x + 1, y - 1));
            }

            return capturablePositions;
        }

        if (Utils.isInBounds(x - 1, y + 1)
            && fileAt(x - 1, y + 1).isOpposing(pawn)) {

            capturablePositions.add(new Position(x - 1, y + 1));
        }

        if (Utils.isInBounds(x + 1, y + 1)
            && fileAt(x + 1, y + 1).isOpposing(pawn)) {

            capturablePositions.add(new Position(x + 1, y + 1));
        }

        return capturablePositions;
    }

    @Override
    public List<Position> getAvailableMovePositions(Position from) {
        int x = from.getX();
        int y = from.getY();

        //the pawn has the option of moving two
        //squares forward provided both squares in front of the pawn are unoccupied
        boolean hasNotMovedYet = y == 1 || y == 6;

        File pawn = fileAt(x, y);
        boolean orientationDown = orientationIsDown(pawn);

        List<Position> availablePositions = getCapturable(orientationDown, pawn, x, y);

        if (orientationDown) {
            if (Utils.isInBounds(x, y - 1) && board.fileAt(x, y - 1).isEmpty()) {
                availablePositions.add(new Position(x, y - 1));

                if (hasNotMovedYet && Utils.isInBounds(x, y - 2) && board.fileAt(x, y - 2).isEmpty()) {
                    availablePositions.add(new Position(x, y - 2));
                }
            }
            return availablePositions;
        }

        if (Utils.isInBounds(x, y + 1) && board.fileAt(x, y + 1).isEmpty()) {
            availablePositions.add(new Position(x, y + 1));

            if (hasNotMovedYet && Utils.isInBounds(x, y + 2) && board.fileAt(x, y + 2).isEmpty()) {
                availablePositions.add(new Position(x, y + 2));
            }
        }

        return availablePositions;
    }


}
