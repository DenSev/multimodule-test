package com.densev.chess.game;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import com.densev.chess.game.board.Piece;
import com.densev.chess.game.moves.*;
import com.densev.chess.players.Player;
import com.densev.chess.players.factory.PlayerFactory;
import com.densev.chess.util.Input;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.Map;

/**
 * Main game class, in addition to playGame method contains some
 * required info such as piece-color representations and piece movement mapping
 */
public enum Game {

    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(Game.class);

    public static final EnumMap<Piece, String> WHITE_PIECES = new EnumMap<>(ImmutableMap.<Piece, String>builder()
        .put(Piece.PAWN, "\u2659")
        .put(Piece.ROOK, "\u2656")
        .put(Piece.KNIGHT, "\u2658")
        .put(Piece.BISHOP, "\u2657")
        .put(Piece.QUEEN, "\u2655")
        .put(Piece.KING, "\u2654")
        .build());

    public static final EnumMap<Piece, String> BLACK_PIECES = new EnumMap<>(ImmutableMap.<Piece, String>builder()
        .put(Piece.PAWN, "\u265F")
        .put(Piece.ROOK, "\u265C")
        .put(Piece.KNIGHT, "\u265E")
        .put(Piece.BISHOP, "\u265D")
        .put(Piece.QUEEN, "\u265B")
        .put(Piece.KING, "\u265A")
        .build());


    private final Board board = new Board();
    public final Map<Piece, ? extends Move> PIECE_MOVEMENT = new EnumMap<Piece, Move>(ImmutableMap.<Piece, Move>builder()
        .put(Piece.PAWN, new PawnMove(board))
        .put(Piece.ROOK, new RookMove(board))
        .put(Piece.KNIGHT, new KnightMove(board))
        .put(Piece.BISHOP, new BishopMove(board))
        .put(Piece.QUEEN, new QueenMove(board))
        .put(Piece.KING, new KingMove(board))
        .build());

    private boolean checkmate;

    /**
     * Method called to play the game
     */
    public void playGame() {
        String play = "yes";

        while ("yes".equals(play)) {
            setCheckmate(false);
            board.fillTheBoard();
            board.print();

            log.info("Please select white player or press enter to use default player.\n" +
                " Available players are: {}", PlayerFactory.PLAYERS);
            Player whitePlayer = createPlayer(PlayerFactory.COMMAND_LINE_PLAYER, board, Color.WHITE);
            log.info("Please select black player or press enter to use default player.\n" +
                " Available players are: {}", PlayerFactory.PLAYERS);
            Player blackPlayer = createPlayer(PlayerFactory.DO_NOTHING_PLAYER, board, Color.BLACK);

            //game will continue until either stalemate or checkmate events were dispatched
            while (!isCheckmate()) {
                whitePlayer.makeAMove();
                board.print();
                if (isCheckmate()) {
                    break;
                }

                blackPlayer.makeAMove();
                board.print();
                if (isCheckmate()) {
                    break;
                }
            }

            log.info("Game is finished, play again? [Enter 'yes' to continue playing]");

            play = Input.getLine();
        }

        Input.shutdown();
    }

    private Player createPlayer(String defaultPlayer, Board board, Color color) {
        String player = Input.getLine();
        if (player == null || player.isEmpty()) {
            player = defaultPlayer;
        }
        if (!PlayerFactory.PLAYERS.contains(player)) {
            log.error("No such player. Please select a valid player.");
            return createPlayer(defaultPlayer, board, color);
        }
        return PlayerFactory.getPlayerForName(player, board, color);
    }

    /**
     * Returns movement class of the piece
     *
     * @param piece - piece
     * @return - movement class
     */
    public Move getPieceMovement(Piece piece) {
        return this.PIECE_MOVEMENT.get(piece);
    }

    public boolean isCheckmate() {
        return checkmate;
    }

    public void setCheckmate(boolean checkmate) {
        this.checkmate = checkmate;
    }
}
