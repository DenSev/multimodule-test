package com.densev.chess.players.factory;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import com.densev.chess.players.Player;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * Created on: 10/24/18
 */
public class PlayerFactory {

    public static final String COMMAND_LINE_PLAYER = "command line";
    public static final String RANDOM_AI_PLAYER = "random ai";
    public static final String DO_NOTHING_PLAYER = "do nothing";

    public static final List<String> PLAYERS = Lists.newArrayList(COMMAND_LINE_PLAYER, RANDOM_AI_PLAYER, DO_NOTHING_PLAYER);

    private static final Map<String, Factory<? extends Player>> FACTORY_MAP = ImmutableMap
        .<String, Factory<? extends Player>>builder()
        .put(COMMAND_LINE_PLAYER, new CommandLinePlayerFactory())
        .put(RANDOM_AI_PLAYER, new RandomAIPlayerFactory())
        .put(DO_NOTHING_PLAYER, new DoNothingPlayerFactory())
        .build();

    public static Player getPlayerForName(String player, Board board, Color color) {
        return FACTORY_MAP.get(player).getPlayer(board, color);
    }
}
