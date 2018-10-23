package com.densev.chess.game.board;

/**
 * Created on: 10/23/18
 */
public class File {

    private Piece piece;
    private Color color;
    private String representation;

    private File() {
        this.representation = " ";
    }

    private File(Piece piece, Color color, String representation) {
        this.piece = piece;
        this.color = color;
        this.representation = representation;
    }

    public static File with(Piece piece, Color color) {
        return new File(piece, color, color.getRepresentation(piece));
    }

    public static File empty() {
        return new File();
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

    public boolean isNotOpposing(File file) {
        return isEmpty() || file.isEmpty() || this.color.equals(file.getColor());
    }

    public boolean isOpposing(File file) {
        return isNotEmpty() && file.isNotEmpty() && !isNotOpposing(file);
    }

    @Override
    public String toString() {
        return getRepresentation();
    }
}
