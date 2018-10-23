package com.densev.chess.game.board;

import com.densev.chess.Application;

import java.util.Map;

/**
 * Created on: 10/23/18
 */
public enum Color {
    BLACK(Application.BLACK_PIECES),
    WHITE(Application.WHITE_PIECES);

    private Map<Piece, String> pieceRepresentations;

    Color(Map<Piece,String> pieceRepresentations) {
        this.pieceRepresentations = pieceRepresentations;
    }


    public String getRepresentation(Piece piece) {
        return this.pieceRepresentations.get(piece);
    }
}
