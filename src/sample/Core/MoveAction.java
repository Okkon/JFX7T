package sample.Core;

import sample.Direction;
import sample.Events.MoveEvent;
import sample.Filters.CanMoveFilter;
import sample.Filters.VacantCellFilter;
import sample.XY;

import java.util.*;

public class MoveAction extends Skill {
    private static final MoveAction INSTANCE = new MoveAction();

    private MoveAction() {
        aimType = AimType.Cell;
        addAimFilter(VacantCellFilter.getInstance());
        addAimFilter(CanMoveFilter.getInstance().setMoveAction(this));
    }

    public static MoveAction getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean endsTurn() {
        return false;
    }

    public Collection<Way> findAllWays(GUnit unit, MoveAction moveAction) {
        Map<GameCell, Way> destinations = new HashMap<>();
        Way start = new Way(unit.getPlace());
        Queue<Way> wayQueue = new ArrayDeque<>();
        wayQueue.add(start);

        while (!wayQueue.isEmpty()) {
            final Way wayPoint = wayQueue.poll();
            Set<Way> ways = moveAction.getWaysFromCell(wayPoint, unit);
            for (Way way : ways) {
                final Way shortestWay = destinations.get(way.getLastCell());
                if (shortestWay == null || way.getLength() < shortestWay.getLength()) {
                    destinations.put(way.getLastCell(), way);
                    wayQueue.add(way);
                }
            }
        }

        final Collection<Way> wayCollection = destinations.values();
        return wayCollection;
    }

    public Set<Way> getWaysFromCell(Way point, GUnit unit) {
        Set<Way> possibleWays = new HashSet<>();
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
        Board board = model.getBoard();
        final List<GameCell> cells = board.getEmptyNearCells(point.getLastCell());
        final Iterator<GameCell> iterator = cells.iterator();
        final XY from = point.getLastCell().getXy();
        while (iterator.hasNext()) {
            GameCell next = iterator.next();
            final Direction direction = Direction.findDirection(from, next.getXy());
            if (direction.isDiagonal()) {
                final GObject obj1 = board.get(XY.get(from.getX(), next.getXy().getY())).getObj();
                final GObject obj2 = board.get(XY.get(next.getXy().getX(), from.getY())).getObj();
                if ((obj1 != null && obj1.blocksMoveFor(unit)) || (obj2 != null && obj2.blocksMoveFor(unit))) {
                    iterator.remove();
                }
            }
        }
        return cells;
    }

    public void go(GUnit unit, GameCell gameCell) {
        Way wayToCell = findWayToCell(unit, gameCell);
        List<GameCell> wayPoints = wayToCell.getWayPoints();
        wayPoints.remove(0);
        for (GameCell cell : wayPoints) {
            step(unit, cell);
        }
    }

    @Override
    public void doAction() {
        final GUnit unit = (GUnit) getOwner();
        go(unit, (GameCell) getAim());
    }

    private void step(GUnit unit, GameCell toCell) {
        MoveEvent moveEvent = new MoveEvent();
        moveEvent.setMoveType(this);
        moveEvent.setUnit(unit);
        moveEvent.setToCell(toCell);
        moveEvent.process();
    }

    private Way createWay(GUnit unit, Way way, GameCell cell) {
        final int stepPrice = calculateStepPrice(way.getLastCell(), cell);
        return unit.getMP() >= stepPrice + way.getLength()
                ? way.addPoint(cell, stepPrice)
                : null;
    }

    public int calculateStepPrice(GameCell fromCell, GameCell toCell) {
        return XY.getDistance(fromCell.getXy(), toCell.getXy());
    }

    public boolean canGetCell(GUnit gUnit, GameCell toCell) {
        return findWayToCell(gUnit, toCell) != null;
    }

    private Way findWayToCell(GUnit unit, GameCell toCell) {
        Way way = new Way(unit.getPlace());
        PriorityQueue<Way> ways = new PriorityQueue<>(
                (o1, o2) -> o1.distanceFunction(toCell) - o2.distanceFunction(toCell)
        );
        ways.addAll(getWaysFromCell(way, unit));
        while (!ways.isEmpty()) {
            way = ways.poll();
            if (toCell.equals(way.getLastCell())) {
                return way;
            } else {
                ways.addAll(getWaysFromCell(way, unit));
            }
        }
        return null;
    }


}
