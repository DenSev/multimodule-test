package com.densev.chess.players;

import com.densev.chess.game.board.Color;

/**
 * Created on: 10/23/18
 */
public class Player {

    private Controller controller;
    private Color color;


    public Player(Controller controller, Color color) {
        this.controller = controller;
        this.color = color;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void makeAMove() {
        controller.makeAMoveAndCheck();
    }
}
