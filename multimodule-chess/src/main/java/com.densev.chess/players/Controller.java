package com.densev.chess.players;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;

/**
 * Created on: 10/23/18
 */
public abstract class Controller {

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

    public abstract boolean makeAMove();

    public boolean makeAMoveAndCheck() {
        boolean checkmate = makeAMove();
        if (checkmate) {
            return true;
        }
        check();
        return false;
    }

    private void check() {
        Color color = board.checkBoard();
        if (color != null) {
            System.out.println(color + " king has been checked!");
        }
    }
}
