package com.densev.chess.players.factory;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import com.densev.chess.players.CommandLinePlayer;
import com.densev.chess.players.Player;

/**
 * Created on: 10/24/18
 */
public class CommandLinePlayerFactory implements Factory<CommandLinePlayer> {

    @Override
    public CommandLinePlayer getPlayer(Board board, Color color) {
        return new CommandLinePlayer(board, color);
    }
}
