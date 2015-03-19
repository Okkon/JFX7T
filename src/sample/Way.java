package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kondrashov on 18.03.2015.
 */
public class Way {
    private final GameCell cell;
    private final int mp;
    private List<Way> points;

    public Way(GameCell place, int mp) {
        this.cell = place;
        this.mp = mp;
        points = new ArrayList<Way>();
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
        way.points.add(this);
        return way;
    }

    public List<GameCell> transformToCellList() {
        List<GameCell> cells = new ArrayList<GameCell>();
        for (Way wayPoint : points) {
            cells.add(wayPoint.getCell());
        }
        cells.remove(0);
        cells.add(getCell());
        return cells;
    }
}
