package com.densev.chess.players;

import com.densev.chess.Application;
import com.densev.chess.Utils;
import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import com.densev.chess.game.board.File;
import com.densev.chess.game.board.Piece;
import com.densev.chess.game.moves.Move;
import com.densev.chess.game.moves.Position;
import com.densev.chess.util.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created on: 10/23/18
 */
public class PlayerController extends Controller {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

    public PlayerController(Board board, Color color) {
        super(board, color);
    }

    public boolean makeAMove() {
        Scanner consoleScanner = new Scanner(System.in);

        boolean hasNotMadeAMove = true;

        while (hasNotMadeAMove) {
            // get x, y of piece to move
            // get piece at x, y
            Map.Entry<Position, File> pieceAndPosition = getPieceAndPosition(consoleScanner);
            Position initialPosition = pieceAndPosition.getKey();
            File file = pieceAndPosition.getValue();

            Move move = Application.PIECE_MOVEMENT.get(file.getPiece());

            Position newPosition = getValidNewPosition(consoleScanner, move, initialPosition);

            boolean checkmate = move.move(initialPosition, newPosition);

            if (checkmate) {
                return true;
            }

            if (Piece.PAWN.equals(file.getPiece())) {
                // check pawn for promotion
            }

            // check for check

            hasNotMadeAMove = false;
        }

        return false;
    }

    private Position getPiecePosition(Scanner scanner) {
        System.out.println("Enter x of piece to move: ");
        int x = Input.getNumber();
        System.out.println("Enter y of piece to move: ");
        int y = Input.getNumber();

        return new Position(x, y);
    }

    private Map.Entry<Position, File> getPieceAndPosition(Scanner scanner) {
        Position position = getPiecePosition(scanner);

        if (!Utils.isInBounds(position.getX(), position.getY())) {
            System.out.println("Your coordinates are out of bounds");
            return getPieceAndPosition(scanner);
        }
        if (board.fileAt(position.getX(), position.getY()).isEmpty()) {
            System.out.println("There is no piece at: " + position.getX() + "," + position.getY());
            return getPieceAndPosition(scanner);
        }
        if (!controlledColor.equals(board.fileAt(position.getX(), position.getY()).getColor())) {
            System.out.println("This is your opponent's piece. Please chose a new one.");
            return getPieceAndPosition(scanner);
        }

        File fileWithPieceToMove = board.fileAt(position.getX(), position.getY());
        System.out.println("You chose to move: " + fileWithPieceToMove.getPiece() + " " + fileWithPieceToMove.getRepresentation());
        return new AbstractMap.SimpleImmutableEntry<>(position, fileWithPieceToMove);
    }

    private Position getNewPosition(Scanner scanner) {
        System.out.println("Enter x of where to move the piece: ");
        int newX = scanner.nextInt();
        System.out.println("Enter y of where to move the piece: ");
        int newY = scanner.nextInt();

        return new Position(newX, newY);
    }

    private Position getValidNewPosition(Scanner scanner, Move move, Position from) {
        Position newPosition = getNewPosition(scanner);

        if (!Utils.isInBounds(newPosition.getX(), newPosition.getY())) {
            System.out.println("Your coordinates are out of bounds");
            System.out.println(move.getAvailableMovePositions(from));
            return getValidNewPosition(scanner, move, from);
        }
        if (controlledColor.equals(board.fileAt(newPosition.getX(), newPosition.getY()).getColor())) {
            System.out.println("This space is occupied by one of your pieces. Please chose a new position.");
            System.out.println(move.getAvailableMovePositions(from));
            return getValidNewPosition(scanner, move, from);
        }
        if (!move.canMoveTo(from, newPosition)) {
            System.out.println("Your piece cannot move to this position");
            System.out.println(move.getAvailableMovePositions(from));
            return getValidNewPosition(scanner, move, from);
        }
        return newPosition;
    }
}
