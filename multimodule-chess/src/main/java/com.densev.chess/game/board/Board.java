package com.densev.chess.game.board;


import com.densev.chess.game.Game;
import com.densev.chess.game.moves.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.densev.chess.game.board.Color.BLACK;
import static com.densev.chess.game.board.Color.WHITE;
import static com.densev.chess.game.board.Piece.*;

/**
 * Game board is an 8 by 8 array of cells
 * <p>
 * Created on: 10/23/18
 */
public class Board {

    private static final Logger log = LoggerFactory.getLogger(Board.class);
    public static final int BOARD_SIZE = 8;
    private Cell[][] cells;

    /**
     * Populates the cells array with cells, cell may contain a piece or be empty
     * There are 8 pawns, 2 rooks, 2 knights, 2 bishops, 1 queen, 1 king on each side
     */
    public void fillTheBoard() {
        this.cells = new Cell[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.cells[i][j] = Cell.empty();
            }
        }

        this.cells[0][0] = Cell.with(ROOK, WHITE);
        this.cells[0][7] = Cell.with(ROOK, WHITE);
        this.cells[7][0] = Cell.with(ROOK, BLACK);
        this.cells[7][7] = Cell.with(ROOK, BLACK);

        this.cells[0][1] = Cell.with(KNIGHT, WHITE);
        this.cells[0][6] = Cell.with(KNIGHT, WHITE);
        this.cells[7][1] = Cell.with(KNIGHT, BLACK);
        this.cells[7][6] = Cell.with(KNIGHT, BLACK);

        this.cells[0][2] = Cell.with(BISHOP, WHITE);
        this.cells[0][5] = Cell.with(BISHOP, WHITE);
        this.cells[7][2] = Cell.with(BISHOP, BLACK);
        this.cells[7][5] = Cell.with(BISHOP, BLACK);

        this.cells[0][3] = Cell.with(QUEEN, WHITE);
        this.cells[7][3] = Cell.with(QUEEN, BLACK);

        this.cells[0][4] = Cell.with(KING, WHITE);
        this.cells[7][4] = Cell.with(KING, BLACK);

        for (int i = 0; i < BOARD_SIZE; i++) {
            this.cells[1][i] = Cell.with(PAWN, WHITE);
            this.cells[6][i] = Cell.with(PAWN, BLACK);
        }
    }

    /**
     * Prints out the board along with row and column indices
     */
    public void print() {
        for (int i = BOARD_SIZE - 1; i >= 0; i--) {
            StringBuilder sb = new StringBuilder()
                .append("[")
                .append(i)
                .append("] ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(this.cells[i][j].getRepresentation()).append("\t");
            }
            log.info(sb.toString());
        }
        log.info("[X]\t[0]\t[1]\t[2]\t[3]\t[4]\t[5]\t[6]\t[7]");
    }

    /**
     * Sets the cell at x,y position to a cell passed in params
     *
     * @param cell - a cell that replaces the cell at x,y
     * @param x    - x coordinate of the cell to be replaced
     * @param y    - y coordinate of the cell to be replaced
     */
    public void setCellAt(Cell cell, int x, int y) {
        this.cells[y][x] = cell;
    }

    /**
     * Returns the cell at x,y position
     *
     * @param x - x coordinate of the cell
     * @param y - y coordinate of the cell
     * @return - the cell at x,y position
     */
    public Cell cellAt(int x, int y) {
        return this.cells[y][x];
    }

    /**
     * Iterates through the board collecting all cells with pieces of
     * required color
     *
     * @param color - color of pieces to search for
     * @return - map with position and file
     */
    public Map<Position, Cell> getCellsOfColor(Color color) {

        Map<Position, Cell> positionsOfPieces = new HashMap<>();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {

                Cell cellAt = cellAt(i, j);
                if (color.equals(cellAt.getColor())) {
                    positionsOfPieces.put(new Position(i, j), cellAt);
                }
            }
        }
        return positionsOfPieces;
    }

    /**
     * Returns {@link Position} of piece of of color
     *
     * @param piece - the piece to find
     * @param color - the color of the piece
     * @return - position of the piece of color or null if no such piece exists on the board
     */
    private Position getPositionOfPiece(Piece piece, Color color) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {

                Cell cellAt = cellAt(i, j);
                if (color.equals(cellAt.getColor()) && piece.equals(cellAt.getPiece())) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    /**
     * Checks if any of the kings have been checked
     *
     * @return - color of the checked king or null if no kings have been checked
     */
    public Color checkBoard() {
        final Map<Position, Cell> whitePieces = getCellsOfColor(WHITE);
        final Position blackKingPosition = getPositionOfPiece(KING, BLACK);
        final boolean blackKingChecked = checkForPieces(whitePieces, blackKingPosition);

        if (blackKingChecked) {
            return Color.BLACK;
        }

        final Map<Position, Cell> blackPieces = getCellsOfColor(BLACK);
        final Position whiteKingPosition = getPositionOfPiece(KING, WHITE);
        final boolean whiteKingChecked = checkForPieces(blackPieces, whiteKingPosition);

        if (whiteKingChecked) {
            return Color.WHITE;
        }

        return null;
    }

    /**
     * Checks if king at opposingKingPositions is in available movement positions of pieces provided in map
     *
     * @param pieces               - map of pieces at positions
     * @param opposingKingPosition - king's positions
     * @return - true if king is checked, false if not
     */
    private boolean checkForPieces(Map<Position, Cell> pieces, Position opposingKingPosition) {
        for (Map.Entry<Position, Cell> pieceAtPosition : pieces.entrySet()) {
            Piece piece = pieceAtPosition.getValue().getPiece();
            List<Position> positions = Game.INSTANCE.getPieceMovement(piece)
                .getAvailableMovePositions(pieceAtPosition.getKey());

            if (positions.contains(opposingKingPosition)) {
                return true;
            }
        }
        return false;
    }
}
