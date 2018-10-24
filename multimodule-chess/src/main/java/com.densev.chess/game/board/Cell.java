package com.densev.chess.game.board;

/**
 * Cell on the board. Either contains a piece of some color or is empty
 * <p>
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

    /**
     * Creates a cell with a piece of provided color
     *
     * @param piece - a piece to put in cell
     * @param color - color of the piece
     * @return - resulting cell
     */
    public static Cell with(Piece piece, Color color) {
        return new Cell(piece, color, color.getRepresentation(piece));
    }

    /**
     * Creates an empty cell
     *
     * @return - an empty cell
     */
    public static Cell empty() {
        return new Cell();
    }

    /**
     * Returns piece representation
     *
     * @return representation
     */
    public String getRepresentation() {
        return "[" + representation + "]";
    }

    public Color getColor() {
        return color;
    }

    public Piece getPiece() {
        return piece;
    }

    /**
     * Sets piece to piece in param, also updates representation accoding to color
     *
     * @param piece - piece to set to
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        this.representation = this.color.getRepresentation(this.piece);
    }

    /**
     * Checks if the cell is not empty
     *
     * @return - true if the cell is not empty, false otherwise
     */
    public boolean isNotEmpty() {
        return this.piece != null;
    }

    /**
     * Checks if the cell is empty
     *
     * @return - treu if the cell is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.piece == null;
    }

    /**
     * Checks that cell does not contain a piece of the opposing color to the piece provided in param
     *
     * @param cell - piece to compare to
     * @return - true if either of the cells are empty or cells are of the same color, false otherwise
     */
    public boolean isNotOpposing(Cell cell) {
        return isEmpty() || cell.isEmpty() || this.color.equals(cell.getColor());
    }

    /**
     * Checks that cell contains a piece of the opposing color to the piece provided in param
     *
     * @param cell - piece to compare to
     * @return - true if both cells are not empty and of different colors, false otherwise
     */
    public boolean isOpposing(Cell cell) {
        return isNotEmpty() && cell.isNotEmpty() && !isNotOpposing(cell);
    }

    @Override
    public String toString() {
        return getRepresentation();
    }
}
