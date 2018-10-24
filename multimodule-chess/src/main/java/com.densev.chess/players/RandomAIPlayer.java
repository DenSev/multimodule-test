package com.densev.chess.players;

import com.densev.chess.game.Game;
import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Cell;
import com.densev.chess.game.board.Color;
import com.densev.chess.game.board.Piece;
import com.densev.chess.game.events.Dispatcher;
import com.densev.chess.game.events.RandomPawnPromoteEvent;
import com.densev.chess.game.moves.Move;
import com.densev.chess.game.moves.Position;
import com.densev.chess.util.RandomBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Random ai player. Randomly chooses a piece to move and randomly chooses a movement position
 * If pawn can be promoted dispatches a {@link RandomPawnPromoteEvent}
 *
 * Created on: 10/23/18
 */
public class RandomAIPlayer extends Player {

    private static final Logger log = LoggerFactory.getLogger(RandomAIPlayer.class);

    public RandomAIPlayer(Board board, Color color) {
        super(board, color);
    }

    public void makeAMove() {
        log.info("RandomAIPlayer is making a move.");
        Map<Position, Cell> positionCellMap = board.getCellsOfColor(controlledColor);
        if (positionCellMap.isEmpty()) {
            throw new IllegalStateException("Ai should have at least a king piece to move, " +
                "otherwise checkmate should have been called");
        }
        int piecesCount = positionCellMap.size();
        int randomPieceNum = RandomBag.getInt(piecesCount);

        List<Map.Entry<Position, Cell>> entryList = new ArrayList<>(positionCellMap.entrySet());
        Map.Entry<Position, Cell> randomPiece = entryList.get(randomPieceNum);
        Piece piece = randomPiece.getValue().getPiece();
        Move move = Game.INSTANCE.getPieceMovement(piece);
        List<Position> availableMoves = move.getAvailableMovePositions(randomPiece.getKey());
        int movesCount = availableMoves.size();
        if (movesCount == 0) {
            log.error("AI has no available moves. I don't know what to do.");
            return;
        }
        int randomMoveNum = RandomBag.getInt(movesCount);
        Position randomMovePosition = availableMoves.get(randomMoveNum);

        move.move(randomPiece.getKey(), randomMovePosition);

        // check pawn for promotion
        if (Piece.PAWN.equals(piece) && randomMovePosition.getY() == 7) {
            Dispatcher.INSTANCE.handleEvent(new RandomPawnPromoteEvent(randomPiece.getValue()));
        }
    }
}
