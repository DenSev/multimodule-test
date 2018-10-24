package com.densev.chess.players;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Player manages piece movement done by real player or ai
 * <p>
 * Created on: 10/23/18
 */
public abstract class Player {

    private static final Logger log = LoggerFactory.getLogger(Player.class);

    Board board;
    Color controlledColor;

    Player(Board board, Color color) {
        this.board = board;
        this.controlledColor = color;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Color getControlledColor() {
        return controlledColor;
    }

    public void setControlledColor(Color controlledColor) {
        this.controlledColor = controlledColor;
    }

    /**
     * Main method of moving a piece, subclasses should implement this
     */
    public abstract void makeAMove();

    /**
     * Makes a move and checks for check
     */
    public void makeAMoveAndCheck() {
        makeAMove();
        check();
    }

    /**
     * Checks for check
     */
    private void check() {
        Color color = board.checkBoard();
        if (color != null) {
            log.info(color + " king has been checked!");
        }
    }
}
