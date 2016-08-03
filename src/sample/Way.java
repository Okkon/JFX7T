package sample;

import java.util.ArrayList;
import java.util.List;

public class Way {
    private final GameCell finishCell;
    private int length;
    private List<GameCell> points;

    public Way(GameCell place) {
        this.finishCell = place;
        this.length = 0;
        points = new ArrayList<>();
    }

    public GameCell getDestinationCell() {
        return finishCell;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (GameCell point : points) {
            if (!first) sb.append(" -> ");
            first = false;
            sb.append(point.getXy());
        }
        sb.append(String.format("(%s)", length));
        return sb.toString();
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
