package com.densev.chess.game;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import com.densev.chess.game.board.Piece;
import com.densev.chess.game.moves.*;
import com.densev.chess.players.*;
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


    private final Board BOARD = new Board();
    public final Map<Piece, ? extends Move> PIECE_MOVEMENT = new EnumMap<Piece, Move>(ImmutableMap.<Piece, Move>builder()
        .put(Piece.PAWN, new PawnMove(BOARD))
        .put(Piece.ROOK, new RookMove(BOARD))
        .put(Piece.KNIGHT, new KnightMove(BOARD))
        .put(Piece.BISHOP, new BishopMove(BOARD))
        .put(Piece.QUEEN, new QueenMove(BOARD))
        .put(Piece.KING, new KingMove(BOARD))
        .build());

    private boolean checkmate;

    /**
     * Method called to play the game
     */
    public void playGame() {
        String play = "yes";

        while ("yes".equals(play)) {
            setCheckmate(false);
            BOARD.fillTheBoard();
            BOARD.print();

            Controller whitePlayer = new PlayerController(BOARD, Color.WHITE);
            Controller blackPlayer = new DoNothingAIController(BOARD, Color.BLACK);

            //game will continue until either stalemate or checkmate events were dispatched
            while (!isCheckmate()) {
                whitePlayer.makeAMove();
                BOARD.print();
                if (isCheckmate()) {
                    break;
                }

                blackPlayer.makeAMove();
                BOARD.print();
                if (isCheckmate()) {
                    break;
                }
            }

            log.info("Game is finished, play again? [Enter 'yes' to continue playing]");

            play = Input.getLine();
        }

        Input.shutdown();
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
