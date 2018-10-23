package com.densev.chess;

import com.densev.chess.game.board.Cell;
import com.densev.chess.game.board.Color;
import com.densev.chess.game.board.Piece;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created on: 10/23/18
 */
public class CellTest {


    @Test
    public void testNotOpposing() {
        Cell x = Cell.with(Piece.BISHOP, Color.BLACK);
        Cell y = Cell.empty();

        assertTrue(y.isNotOpposing(x));
        assertTrue(x.isNotOpposing(y));
    }

    @Test
    public void testOpposing() {
        Cell x = Cell.with(Piece.BISHOP, Color.BLACK);
        Cell y = Cell.empty();

        assertFalse(y.isOpposing(x));
        assertFalse(x.isOpposing(y));
    }
}
