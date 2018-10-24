package com.densev.chess.players.factory;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import com.densev.chess.players.RandomAIPlayer;

/**
 * Created on: 10/24/18
 */
public class RandomAIPlayerFactory implements Factory<RandomAIPlayer> {

    @Override
    public RandomAIPlayer getPlayer(Board board, Color color) {
        return new RandomAIPlayer(board, color);
    }
}
