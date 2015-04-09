package sample;

import java.util.*;

public class DefaultMoveType implements MoveType {

    @Override
    public Set<Way> getWayFromCell(Way point, GUnit unit) {
        Set<Way> possibleWays = new HashSet<Way>();
        List<GameCell> cells = getCellsWhereUnitCanGo(point, unit);
        for (GameCell cell : cells) {
            Way way = createWay(unit, point, cell);
            if (way != null) {
                possibleWays.add(way);
            }
        }
        return possibleWays;
    }

    private List<GameCell> getCellsWhereUnitCanGo(Way point, GUnit unit) {
        final List<GameCell> cells = GameModel.MODEL.getEmptyNearCells(point.getCell());
        final Iterator<GameCell> iterator = cells.iterator();
        final Map<XY, GameCell> board = GameModel.MODEL.getBoard();
        final XY from = point.getCell().getXy();
        while (iterator.hasNext()) {
            GameCell next = iterator.next();
            final Direction direction = Direction.findDirection(from, next.getXy());
            if (direction.isDiagonal()) {
                final GObject obj = board.get(new XY(from.getX(), next.getXy().getY())).getObj();
                final GObject obj2 = board.get(new XY(next.getXy().getX(), from.getY())).getObj();
                if ((obj != null && obj.blocksMoveFor(unit)) || (obj2 != null && obj2.blocksMoveFor(unit))) {
                    iterator.remove();
                }
            }
        }
        return cells;
    }

    @Override
    public void go(GUnit unit, GameCell gameCell) {
        final Collection<Way> lastFoundWays = GameModel.MODEL.getLastFoundWays();
        final List<GameCell> wayToCell = getWayToCell(lastFoundWays, gameCell);
        for (GameCell cell : wayToCell) {
            GameModel.MODEL.log("base", "UnitMove", unit.getName(), unit.getXy(), cell.getXy());
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

    private Way createWay(GUnit unit, Way way, GameCell cell) {
        int mp = way.getMp();
        final int distance = XY.getDistance(way.getCell().getXy(), cell.getXy());
        return mp >= distance
                ? way.next(cell, mp - distance)
                : null;
    }
}
