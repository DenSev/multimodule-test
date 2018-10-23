package com.densev.chess;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import com.densev.chess.game.board.Piece;
import com.densev.chess.game.moves.*;
import com.densev.chess.players.AIController;
import com.densev.chess.players.Player;
import com.densev.chess.players.PlayerController;
import com.densev.chess.util.Input;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created on: 10/23/18
 */
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    public static final Board BOARD = new Board();

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


    public static final Map<Piece, ? extends Move> PIECE_MOVEMENT = new EnumMap<Piece, Move>(ImmutableMap.<Piece, Move>builder()
            .put(Piece.PAWN, new PawnMove(BOARD))
            .put(Piece.ROOK, new RookMove(BOARD))
            .put(Piece.KNIGHT, new KnightMove(BOARD))
            .put(Piece.BISHOP, new BishopMove(BOARD))
            .put(Piece.QUEEN, new QueenMove(BOARD))
            .put(Piece.KING, new KingMove(BOARD))
            .build());

    public static void main(String... args) {

        String play = "yes";
        while ("yes".equals(play)) {
            playGame();

            log.info("Game is finished, play again? [Enter 'yes' to continue playing]");


            play = Input.getLine();
        }

        Input.shutdown();
    }

    private static void playGame() {
        BOARD.fillTheBoard();
        BOARD.print();

        Player humanPlayer = new Player(new PlayerController(BOARD, Color.WHITE), Color.WHITE);
        Player aiPlayer = new Player(new AIController(BOARD, Color.BLACK), Color.BLACK);

        boolean checkMate = false;

        while (!checkMate) {
            checkMate = humanPlayer.makeAMove();
            BOARD.print();
            if (checkMate) {
                break;
            }

            checkMate = aiPlayer.makeAMove();
            BOARD.print();
            if (checkMate) {
                break;
            }
        }
    }
}
