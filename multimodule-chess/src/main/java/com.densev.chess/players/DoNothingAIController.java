package com.densev.chess.players;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ai controller that does nothing, made for testing
 *
 * Created on: 10/24/18
 */
public class DoNothingAIController extends Controller {

    private static final Logger log = LoggerFactory.getLogger(DoNothingAIController.class);

    public DoNothingAIController(Board board, Color color) {
        super(board, color);
    }

    @Override
    public void makeAMove() {
        log.info("Does nothing.");
    }
}
