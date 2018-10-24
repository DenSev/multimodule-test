package com.densev.chess.players;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on: 10/23/18
 */
public abstract class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    Board board;
    Color controlledColor;

    public Controller(Board board, Color color) {
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

    public abstract void makeAMove();

    public void makeAMoveAndCheck() {
        makeAMove();
        check();
    }

    private void check() {
        Color color = board.checkBoard();
        if (color != null) {
            log.info(color + " king has been checked!");
        }
    }
}
