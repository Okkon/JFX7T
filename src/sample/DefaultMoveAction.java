package sample;

import java.util.*;

public class DefaultMoveAction extends Skill implements MoveAction {
    public DefaultMoveAction() {
        endsTurn = false;
    }

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
        final List<GameCell> cells = GameModel.MODEL.getEmptyNearCells(point.getDestinationCell());
        final Iterator<GameCell> iterator = cells.iterator();
        final Map<XY, GameCell> board = GameModel.MODEL.getBoard();
        final XY from = point.getDestinationCell().getXy();
        while (iterator.hasNext()) {
            GameCell next = iterator.next();
            final Direction direction = Direction.findDirection(from, next.getXy());
            if (direction.isDiagonal()) {
                final GObject obj1 = board.get(new XY(from.getX(), next.getXy().getY())).getObj();
                final GObject obj2 = board.get(new XY(next.getXy().getX(), from.getY())).getObj();
                if ((obj1 != null && obj1.blocksMoveFor(unit)) || (obj2 != null && obj2.blocksMoveFor(unit))) {
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

    @Override
    public void act(Selectable obj) {
        final GUnit unit = (GUnit) getOwner();
        go(unit, (GameCell) obj);
    }

    private void step(GUnit unit, GameCell toCell) {
        final int stepPrice = calculateStepPrice(unit.getPlace(), toCell);
        unit.shift(toCell);
        unit.looseMP(stepPrice);
    }

    private List<GameCell> getWayToCell(Collection<Way> lastFoundWays, GameCell gameCell) {
        Way unitWay = null;
        for (Way way : lastFoundWays) {
            if (way.getDestinationCell().equals(gameCell)) {
                unitWay = way;
                break;
            }
        }
        if (unitWay != null) {
            return unitWay.getWayPoints();
        }
        return Collections.EMPTY_LIST;
    }

    private Way createWay(GUnit unit, Way way, GameCell cell) {
        int wayLength = way.getLength();
        final int stepPrice = calculateStepPrice(way.getDestinationCell(), cell);
        return unit.getMP() >= stepPrice + wayLength
                ? way.next(cell, stepPrice + wayLength)
                : null;
    }

    private int calculateStepPrice(GameCell fromCell, GameCell toCell) {
        return XY.getDistance(fromCell.getXy(), toCell.getXy());
    }
}
