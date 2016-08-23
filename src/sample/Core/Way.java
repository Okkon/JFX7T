package sample.Core;

import java.util.ArrayList;
import java.util.List;

public class Way {
    private int length;
    private List<GameCell> points;

    private Way() {
        this.length = 0;
        points = new ArrayList<>();
    }

    public Way(GameCell place) {
        this();
        points.add(place);
    }

    public GameCell getLastCell() {
        int index = points.size() - 1;
        return index >= 0 ? points.get(index) : null;
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

    public Way addPoint(GameCell cell, int length) {
        final Way newWay = new Way();
        newWay.length = this.length + length;
        newWay.points.addAll(this.points);
        newWay.points.add(cell);
        return newWay;
    }

    public List<GameCell> getWayPoints() {
        return points;
    }
}
