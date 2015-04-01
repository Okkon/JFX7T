package sample;

import java.util.ArrayList;
import java.util.List;

public class Way {
    private final GameCell cell;
    private final int mp;
    private List<GameCell> points;

    public Way(GameCell place, int mp) {
        this.cell = place;
        this.mp = mp;
        points = new ArrayList<GameCell>();
    }

    public GameCell getCell() {
        return cell;
    }

    public int getMp() {
        return mp;
    }

    public Way next(GameCell cell, int mp) {
        final Way way = new Way(cell, mp);
        way.points.addAll(this.points);
        way.points.add(cell);
        return way;
    }

    public List<GameCell> transformToCellList() {
        List<GameCell> cells = new ArrayList<GameCell>();
        for (GameCell wayPoint : points) {
            cells.add(wayPoint);
        }
        cells.remove(0);
        cells.add(getCell());
        return cells;
    }
}
