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

    @Override
    public String toString() {
        return cell.getXy().toString() + " -> " + length;
    }

    public Way next(GameCell cell, int length) {
        final Way way = new Way(cell);
        way.length = length;
        way.points.addAll(this.points);
        way.points.add(cell);
        return way;
    }

    public List<GameCell> getWayPoints() {
        return points;
    }
}
