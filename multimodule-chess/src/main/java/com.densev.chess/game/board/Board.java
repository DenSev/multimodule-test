package com.densev.chess.game.board;


import com.densev.chess.Application;
import com.densev.chess.game.moves.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.densev.chess.game.board.Color.BLACK;
import static com.densev.chess.game.board.Color.WHITE;
import static com.densev.chess.game.board.Piece.*;

/**
 * Created on: 10/23/18
 */
public class Board {

    public static final int BOARD_SIZE = 8;
    private File[][] files;


    public void fillTheBoard() {
        this.files = new File[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.files[i][j] = File.empty();
            }
        }

        this.files[0][0] = File.with(ROOK, WHITE);
        this.files[0][7] = File.with(ROOK, WHITE);
        this.files[7][0] = File.with(ROOK, BLACK);
        this.files[7][7] = File.with(ROOK, BLACK);

        this.files[0][1] = File.with(KNIGHT, WHITE);
        this.files[0][6] = File.with(KNIGHT, WHITE);
        this.files[7][1] = File.with(KNIGHT, BLACK);
        this.files[7][6] = File.with(KNIGHT, BLACK);

        this.files[0][2] = File.with(BISHOP, WHITE);
        this.files[0][5] = File.with(BISHOP, WHITE);
        this.files[7][2] = File.with(BISHOP, BLACK);
        this.files[7][5] = File.with(BISHOP, BLACK);

        this.files[0][3] = File.with(QUEEN, WHITE);
        this.files[7][3] = File.with(QUEEN, BLACK);

        this.files[0][4] = File.with(KING, WHITE);
        this.files[7][4] = File.with(KING, BLACK);

        for (int i = 0; i < BOARD_SIZE; i++) {
            this.files[1][i] = File.with(PAWN, WHITE);
            this.files[6][i] = File.with(PAWN, BLACK);
        }
    }


    public void print() {
        for (int i = BOARD_SIZE - 1; i >= 0; i--) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(this.files[i][j].getRepresentation() + "\t");
            }
            System.out.println();
        }
    }

    public File[][] getFiles() {
        return files;
    }

    public void setFileAt(File file, int x, int y) {
        this.files[y][x] = file;
    }

    public File fileAt(int x, int y) {
        return this.files[y][x];
    }

    /**
     * Iterates through the board collecting all files with pieces of
     * required color
     *
     * @param color - color of pieces to search for
     * @return - map with position and file
     */
    public Map<Position, File> getFilesOfColor(Color color) {

        Map<Position, File> positionsOfPieces = new HashMap<>();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {

                File fileAt = fileAt(i, j);
                if (color.equals(fileAt.getColor())) {
                    positionsOfPieces.put(new Position(i, j), fileAt);
                }
            }
        }
        return positionsOfPieces;
    }

    public Position getPositionOfPiece(Piece piece, Color color) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {

                File fileAt = fileAt(i, j);
                if (color.equals(fileAt.getColor()) && piece.equals(fileAt.getPiece())) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }


    public Color checkBoard() {
        final Map<Position, File> whitePieces = getFilesOfColor(WHITE);
        final Position blackKingPosition = getPositionOfPiece(KING, BLACK);
        final boolean blackKingChecked = checkForPieces(whitePieces, blackKingPosition);

        if (blackKingChecked) {
            return Color.WHITE;
        }

        final Map<Position, File> blackPieces = getFilesOfColor(BLACK);
        final Position whiteKingPosition = getPositionOfPiece(KING, WHITE);
        final boolean whiteKingChecked = checkForPieces(blackPieces, whiteKingPosition);

        if (whiteKingChecked) {
            return Color.BLACK;
        }

        return null;
    }

    public boolean checkForPieces(Map<Position, File> pieces, Position opposingKingPosition) {
        for (Map.Entry<Position, File> pieceAtPosition : pieces.entrySet()) {
            Piece piece = pieceAtPosition.getValue().getPiece();
            List<Position> positions = Application.PIECE_MOVEMENT
                .get(piece)
                .getAvailableMovePositions(pieceAtPosition.getKey());

            if (positions.contains(opposingKingPosition)) {
                return true;
            }
        }
        return false;
    }
}
