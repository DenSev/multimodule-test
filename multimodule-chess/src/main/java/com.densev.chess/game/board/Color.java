package com.densev.chess.game.board;

import com.densev.chess.game.Game;

import java.util.Map;

/**
 * Colors of the pieces, additionally maps piece representations to color
 *
 * Created on: 10/23/18
 */
public enum Color {
    BLACK(Game.BLACK_PIECES),
    WHITE(Game.WHITE_PIECES);

    private Map<Piece, String> pieceRepresentations;

    Color(Map<Piece,String> pieceRepresentations) {
        this.pieceRepresentations = pieceRepresentations;
    }

    public String getRepresentation(Piece piece) {
        return this.pieceRepresentations.get(piece);
    }

}
