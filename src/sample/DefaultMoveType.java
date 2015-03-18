package sample;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kondrashov on 18.03.2015.
 */
public class DefaultMoveType implements MoveType {

    @Override
    public Set<Way> getWayPoints(Way point, GUnit unit) {
        Set<Way> possibleWays = new HashSet<Way>();
        List<GameCell> cells = GameModel.MODEL.getNearCells(point.getCell());
        for (GameCell cell : cells) {
            Way way = createWay(unit, point, cell);
            if (way != null) {
                possibleWays.add(way);
            }
        }
        return possibleWays;
    }

    private Way createWay(GUnit unit, Way point, GameCell cell) {
        int mp = point.getMp();
        final int distance = XY.getDistance(point.getCell().getXy(), cell.getXy());
        return mp >= distance
                ? point.next(cell, mp - distance)
                : null;
    }
}
