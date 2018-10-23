package com.densev.chess.game.board;

/**
 * Created on: 10/23/18
 */
public class Cell {

    private Piece piece;
    private Color color;
    private String representation;

    private Cell() {
        this.representation = " ";
    }

    private Cell(Piece piece, Color color, String representation) {
        this.piece = piece;
        this.color = color;
        this.representation = representation;
    }

    public static Cell with(Piece piece, Color color) {
        return new Cell(piece, color, color.getRepresentation(piece));
    }

    public static Cell empty() {
        return new Cell();
    }

    public boolean isNotEmpty() {
        return this.piece != null;
    }
    public boolean isEmpty(){
        return this.piece == null;
    }

    public String getRepresentation() {
        return "[" + representation + "]";
    }

    public Color getColor() {
        return color;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isNotOpposing(Cell cell) {
        return isEmpty() || cell.isEmpty() || this.color.equals(cell.getColor());
    }

    public boolean isOpposing(Cell cell) {
        return isNotEmpty() && cell.isNotEmpty() && !isNotOpposing(cell);
    }

    @Override
    public String toString() {
        return getRepresentation();
    }
}
