package com.densev.chess.players.factory;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import com.densev.chess.players.Player;

/**
 * Created on: 10/24/18
 */
public interface Factory<T extends Player> {

    T getPlayer(Board board, Color color);
}
