package com.densev.chess.game.moves;

import com.densev.chess.game.board.Board;
import com.densev.chess.game.board.Cell;
import com.densev.chess.util.BoardUtils;

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

    /**
     * Returns a new position if it is in bounds an is empty or occupied by a piece
     * from opposing team
     *
     * @param cell - cell occupied by the knight piece that is being moved
     * @param x    - target cell x
     * @param y    - target cell y
     * @return - a new position or null if x,y is out of bounds or x,y cell is occupied by
     * a piece on the same team
     */
    private Position getIfAvailable(Cell cell, int x, int y) {
        if (BoardUtils.isInBounds(x, y)) {
            Cell targetCell = fileAt(x, y);
            if (targetCell.isOpposing(cell) || targetCell.isEmpty()) {
                return new Position(x, y);
            }

        }
        return null;
    }

    /**
     * Add new position at x,y to list of available positions, if the knight piece which is being moved can move there
     *
     * @param positions - list of available positions
     * @param cell      - cell occupied by the knight piece that is being moved
     * @param x         - target cell x
     * @param y         - target cell y
     */
    private void addIfAvailable(List<Position> positions, Cell cell, int x, int y) {
        Position position = getIfAvailable(cell, x, y);
        if (position != null) {
            positions.add(position);
        }
    }
}
