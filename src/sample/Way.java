package sample;

import java.util.ArrayList;
import java.util.List;

public class Way {
    private final GameCell cell;
    private int length;
    private List<GameCell> points;

    public Way(GameCell place) {
        this.cell = place;
        this.length = 0;
        points = new ArrayList<GameCell>();
    }

    public GameCell getDestinationCell() {
        return cell;
    }

    public int getLength() {
        return length;
    }

    public Way next(GameCell cell, int length) {
        final Way way = new Way(cell);
        this.length = length;
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
        cells.add(getDestinationCell());
        return cells;
    }
}
