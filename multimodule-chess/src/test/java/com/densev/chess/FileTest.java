package com.densev.chess;

import com.densev.chess.game.board.Color;
import com.densev.chess.game.board.File;
import com.densev.chess.game.board.Piece;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created on: 10/23/18
 */
public class FileTest {


    @Test
    public void testNotOpposing() {
        File x = File.with(Piece.BISHOP, Color.BLACK);
        File y = File.empty();

        assertTrue(y.isNotOpposing(x));
        assertTrue(x.isNotOpposing(y));
    }

    @Test
    public void testOpposing() {
        File x = File.with(Piece.BISHOP, Color.BLACK);
        File y = File.empty();

        assertFalse(y.isOpposing(x));
        assertFalse(x.isOpposing(y));
    }
}
