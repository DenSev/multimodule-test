package com.densev.chess.players;

import com.densev.chess.game.Game;
import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Cell;
import com.densev.chess.game.board.Color;
import com.densev.chess.game.board.Piece;
import com.densev.chess.game.events.Dispatcher;
import com.densev.chess.game.events.PawnPromoteEvent;
import com.densev.chess.game.moves.Move;
import com.densev.chess.game.moves.Position;
import com.densev.chess.util.BoardUtils;
import com.densev.chess.util.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Command line player, real player decides piece movement
 * <p>
 * Created on: 10/23/18
 */
public class CommandLinePlayer extends Player {

    private static final Logger log = LoggerFactory.getLogger(CommandLinePlayer.class);

    public CommandLinePlayer(Board board, Color color) {
        super(board, color);
    }

    /**
     * Makes player choose a move, recursively calls itself
     * if chosen piece has no movement positions available
     */
    public void makeAMove() {

        // get piece at x, y
        Map.Entry<Position, Cell> pieceAndPosition = getPieceAndPosition();
        // get piece initial position
        Position initialPosition = pieceAndPosition.getKey();
        // get piece cell
        Cell cell = pieceAndPosition.getValue();
        // gen piece move component
        Move move = Game.INSTANCE.getPieceMovement(cell.getPiece());
        if (move.getAvailableMovePositions(initialPosition).isEmpty()) {
            log.error("Chose piece has no available movement. Choose another piece.");
            makeAMove();
            return;
        }

        // make a player choose a valid position
        Position newPosition = getValidNewPosition(move, initialPosition);
        // make a move
        move.move(initialPosition, newPosition);

        // check for pawn promotion
        if (Piece.PAWN.equals(cell.getPiece()) && newPosition.getY() == 7) {
            Dispatcher.INSTANCE.handleEvent(new PawnPromoteEvent(cell));
        }
    }

    /**
     * Returns position with x,y entered by player
     *
     * @return - a new position
     */
    private Position getPiecePosition() {
        log.info("Enter x of piece to move: ");
        int x = Input.getInteger();
        log.info("Enter y of piece to move: ");
        int y = Input.getInteger();

        return new Position(x, y);
    }

    /**
     * Makes the player choose a valid piece
     *
     * @return cell and cell position of piece to move
     */
    private Map.Entry<Position, Cell> getPieceAndPosition() {
        Position position = getPiecePosition();

        if (!BoardUtils.isInBounds(position.getX(), position.getY())) {
            log.error("Your coordinates are out of bounds");
            return getPieceAndPosition();
        }
        if (board.cellAt(position.getX(), position.getY()).isEmpty()) {
            log.error("There is no piece at: {},{}", position.getX(), position.getY());
            return getPieceAndPosition();
        }
        if (!controlledColor.equals(board.cellAt(position.getX(), position.getY()).getColor())) {
            log.error("This is your opponent's piece. Please chose a new one.");
            return getPieceAndPosition();
        }

        Cell cellWithPieceToMove = board.cellAt(position.getX(), position.getY());
        log.info("You chose to move: {} {}", cellWithPieceToMove.getPiece(), cellWithPieceToMove.getRepresentation());
        return new AbstractMap.SimpleImmutableEntry<>(position, cellWithPieceToMove);
    }

    /**
     * Get position at x,y that piece would be moved to
     *
     * @return - a new position at x,y
     */
    private Position getNewPosition() {
        log.info("Enter x of where to move the piece: ");
        int newX = Input.getInteger();
        log.info("Enter y of where to move the piece: ");
        int newY = Input.getInteger();

        return new Position(newX, newY);
    }

    /**
     * Makes the player choose a valid position to move to
     * Checks that the piece at from can move to chosen position
     *
     * @param move - the piece's move component
     * @param from - pieces current position
     * @return - new position to move to
     */
    private Position getValidNewPosition(Move move, Position from) {
        log.debug("Available positions: {}", move.getAvailableMovePositions(from));
        Position newPosition = getNewPosition();

        if (!BoardUtils.isInBounds(newPosition.getX(), newPosition.getY())) {
            log.info("Your coordinates are out of bounds");
            return getValidNewPosition(move, from);
        }
        if (controlledColor.equals(board.cellAt(newPosition.getX(), newPosition.getY()).getColor())) {
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
