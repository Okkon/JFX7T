package sample;

import java.util.*;

/**
 * Created by kondrashov on 18.03.2015.
 */
public class DefaultMoveType implements MoveType {

    @Override
    public Set<Way> getWayPoints(Way point, GUnit unit) {
        Set<Way> possibleWays = new HashSet<Way>();
        List<GameCell> cells = GameModel.MODEL.getEmptyNearCells(point.getCell());
        for (GameCell cell : cells) {
            Way way = createWay(unit, point, cell);
            if (way != null) {
                possibleWays.add(way);
            }
        }
        return possibleWays;
    }

    @Override
    public void go(GUnit unit, GameCell gameCell) {
        final Collection<Way> lastFoundWays = GameModel.MODEL.getLastFoundWays();
        final List<GameCell> wayToCell = getWayToCell(lastFoundWays, gameCell);
        for (GameCell cell : wayToCell) {
            GameModel.MODEL.log(unit + " move from " + unit.getXy() + " to " + cell.getXy());
            step(unit, cell);
        }
    }

    private void step(GUnit unit, GameCell cell) {
        final int distance = XY.getDistance(unit.getXy(), cell.getXy());
        unit.shift(cell);
        unit.looseMP(distance);
    }

    private List<GameCell> getWayToCell(Collection<Way> lastFoundWays, GameCell gameCell) {
        Way unitWay = null;
        for (Way way : lastFoundWays) {
            if (way.getCell().equals(gameCell)) {
                unitWay = way;
                break;
            }
        }
        if (unitWay != null) {
            return unitWay.transformToCellList();
        }
        return Collections.EMPTY_LIST;
    }

    private Way createWay(GUnit unit, Way point, GameCell cell) {
        int mp = point.getMp();
        final int distance = XY.getDistance(point.getCell().getXy(), cell.getXy());
        return mp >= distance
                ? point.next(cell, mp - distance)
                : null;
    }
}
