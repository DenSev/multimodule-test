package com.densev.chess.game.moves;

import com.densev.chess.util.BoardUtils;
import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Knight can move one square along any rank or file and then at an angle.
 * The knight´s movement can also be viewed as an “L” or “7″ laid out at
 * any horizontal or vertical angle.
 * [ ] [X] [ ] [X] [ ]
 * [X] [ ] [ ] [ ] [X]
 * [ ] [ ] [K] [ ] [ ]
 * [X] [ ] [ ] [ ] [X]
 * [ ] [X] [ ] [X] [ ]
 *
 *
 * <p>
 * Created on: 10/23/18
 */
public class KnightMove extends Move {

    public KnightMove(Board board) {
        super(board);
    }

    @Override
    public List<Position> getAvailableMovePositions(Position from) {
        List<Position> availablePositions = new ArrayList<>();
        final int x = from.getX();
        final int y = from.getY();
        Cell knight = fileAt(x, y);

        //upper right
        addIfAvailable(availablePositions, knight, x + 1, y + 2);
        addIfAvailable(availablePositions, knight, x + 2, y + 1);
        //upper left
        addIfAvailable(availablePositions, knight, x - 1, y + 2);
        addIfAvailable(availablePositions, knight, x - 2, y + 1);
        //lower right
        addIfAvailable(availablePositions, knight, x + 1, y - 2);
        addIfAvailable(availablePositions, knight, x + 2, y - 1);
        //lover left
        addIfAvailable(availablePositions, knight, x - 1, y - 2);
        addIfAvailable(availablePositions, knight, x - 2, y - 1);

        return availablePositions;
    }

    private Position getIfAvailable(Cell cell, int x, int y) {
        if (BoardUtils.isInBounds(x, y)) {
            Cell targetCell = fileAt(x, y);
            if (targetCell.isOpposing(cell) || targetCell.isEmpty()) {
                return new Position(x, y);
            }

        }
        return null;
    }

    private void addIfAvailable(List<Position> positions, Cell cell, int x, int y) {
        Position position = getIfAvailable(cell, x, y);
        if (position != null) {
            positions.add(position);
        }
    }
}
