package com.densev.chess.players;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on: 10/23/18
 */
public class AIController extends Controller {

    private static final Logger log = LoggerFactory.getLogger(AIController.class);

    public AIController(Board board, Color color) {
        super(board, color);
    }

    public boolean makeAMove() {

        log.info("AIController does nothing for now.");
        //get all available pieces
        //get a random piece
        //get all available positions for movement
        //get random position
        return false;
    }
}
