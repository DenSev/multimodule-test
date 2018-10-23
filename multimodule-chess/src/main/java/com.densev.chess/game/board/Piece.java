package com.densev.chess.game.board;

/**
 * Created on: 10/23/18
 */
public enum Piece {

    QUEEN(1),
    KING(1),
    BISHOP(2),
    KNIGHT(2),
    ROOK(2),
    PAWN(8);

    private int count;


    Piece(int count) {

    }
}
