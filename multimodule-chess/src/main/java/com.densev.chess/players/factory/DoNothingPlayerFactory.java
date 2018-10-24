package com.densev.chess.players.factory;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import com.densev.chess.players.DoNothingAIPlayer;

/**
 * Created on: 10/24/18
 */
public class DoNothingPlayerFactory implements Factory<DoNothingAIPlayer> {

    @Override
    public DoNothingAIPlayer getPlayer(Board board, Color color) {
        return new DoNothingAIPlayer(board, color);
    }
}
