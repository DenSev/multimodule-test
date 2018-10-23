package com.densev.chess.players;

import com.densev.chess.game.Game;
import com.densev.chess.util.BoardUtils;
import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Cell;
import com.densev.chess.game.board.Color;
import com.densev.chess.game.board.Piece;
import com.densev.chess.game.moves.Move;
import com.densev.chess.game.moves.Position;
import com.densev.chess.util.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Created on: 10/23/18
 */
public class PlayerController extends Controller {

    private static final Logger log = LoggerFactory.getLogger(PlayerController.class);

    public PlayerController(Board board, Color color) {
        super(board, color);
    }

    public boolean makeAMove() {

        boolean hasNotMadeAMove = true;

        while (hasNotMadeAMove) {
            // get x, y of piece to move
            // get piece at x, y
            Map.Entry<Position, Cell> pieceAndPosition = getPieceAndPosition();
            Position initialPosition = pieceAndPosition.getKey();
            Cell cell = pieceAndPosition.getValue();

            Move move = Game.PIECE_MOVEMENT.get(cell.getPiece());

            Position newPosition = getValidNewPosition(move, initialPosition);

            boolean checkmate = move.move(initialPosition, newPosition);

            if (checkmate) {
                return true;
            }

            if (Piece.PAWN.equals(cell.getPiece())) {
                // check pawn for promotion
            }

            // check for check

            hasNotMadeAMove = false;
        }

        return false;
    }

    private Position getPiecePosition() {
        log.info("Enter x of piece to move: ");
        int x = Input.getNumber();
        log.info("Enter y of piece to move: ");
        int y = Input.getNumber();

        return new Position(x, y);
    }

    private Map.Entry<Position, Cell> getPieceAndPosition() {
        Position position = getPiecePosition();

        if (!BoardUtils.isInBounds(position.getX(), position.getY())) {
            log.error("Your coordinates are out of bounds");
            return getPieceAndPosition();
        }
        if (board.fileAt(position.getX(), position.getY()).isEmpty()) {
            log.error("There is no piece at: {},{}", position.getX(), position.getY());
            return getPieceAndPosition();
        }
        if (!controlledColor.equals(board.fileAt(position.getX(), position.getY()).getColor())) {
            log.error("This is your opponent's piece. Please chose a new one.");
            return getPieceAndPosition();
        }

        Cell cellWithPieceToMove = board.fileAt(position.getX(), position.getY());
        log.info("You chose to move: {} {}", cellWithPieceToMove.getPiece(), cellWithPieceToMove.getRepresentation());
        return new AbstractMap.SimpleImmutableEntry<>(position, cellWithPieceToMove);
    }

    private Position getNewPosition() {
        log.info("Enter x of where to move the piece: ");
        int newX = Input.getNumber();
        log.info("Enter y of where to move the piece: ");
        int newY = Input.getNumber();

        return new Position(newX, newY);
    }

    private Position getValidNewPosition(Move move, Position from) {
        log.debug("Available positions: {}", move.getAvailableMovePositions(from));
        Position newPosition = getNewPosition();

        if (!BoardUtils.isInBounds(newPosition.getX(), newPosition.getY())) {
            log.info("Your coordinates are out of bounds");
            return getValidNewPosition(move, from);
        }
        if (controlledColor.equals(board.fileAt(newPosition.getX(), newPosition.getY()).getColor())) {
            log.info("This space is occupied by one of your pieces. Please chose a new position.");
            return getValidNewPosition(move, from);
        }
        if (!move.canMoveTo(from, newPosition)) {
            log.info("Your piece cannot move to this position");
            return getValidNewPosition(move, from);
        }
        return newPosition;
    }
}
