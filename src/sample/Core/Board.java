package sample.Core;

import sample.XY;

import java.util.*;

/**
 * Created by olko1016 on 10/28/2016.
 */
public class Board {
    private Map<XY, GameCell> board = new HashMap<>();


    public Board(int x, int y) {
        init(x, y);
    }

    private void init(int x, int y) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                final XY xy = XY.get(i, j);
                board.put(xy, new GameCell(xy));
            }
        }
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                final XY xy = XY.get(i, j);
                GameCell cell = board.get(xy);
                GameCell rightCell = board.get(xy.changeX(1));
                GameCell bottomCell = board.get(xy.changeY(1));
                GameCell diagonalCell = board.get(xy.changeX(1).changeY(1));
                if (board.get(rightCell) != null) {
                    cell.link(rightCell, XY.straightLength);
                }
                if (board.get(rightCell) != null) {
                    cell.link(bottomCell, XY.straightLength);
                }
                if (board.get(rightCell) != null) {
                    cell.link(diagonalCell, XY.diagonalLength);
                }
            }
        }
    }

    public GameCell get(XY xy) {
        return board.get(xy);
    }

    public List<GameCell> getNearCells(GameCell cell) {
        List<GameCell> cells = new ArrayList<>();
        XY xy = cell.getXy();
        for (GameCell gameCell : board.values()) {
            if (XY.isNear(xy, gameCell.getXy())) {
                cells.add(gameCell);
            }
        }
        return cells;
    }

    public List<GameCell> getEmptyNearCells(GameCell cell) {
        final List<GameCell> nearCells = getNearCells(cell);
        final Iterator<GameCell> iterator = nearCells.iterator();
        while (iterator.hasNext()) {
            GameCell next = iterator.next();
            if (next.isNotEmpty()) {
                iterator.remove();
            }
        }
        return nearCells;
    }


    public Collection<GameCell> getAllCells() {
        return board.values();
    }

    public boolean isNear(GObject obj1, PlaceHaving obj2) {
        return XY.isNear(obj1.getXy(), obj2.getXy());
    }
}
