package com.densev.chess.game.moves;

import java.util.Objects;

/**
 * Position, represents {@link com.densev.chess.game.board.Cell}'s
 * x and y on {@link com.densev.chess.game.board.Board}
 *
 * Created on: 10/23/18
 */
public class Position {

    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
            y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
