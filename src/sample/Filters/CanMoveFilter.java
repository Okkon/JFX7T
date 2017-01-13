package sample.Filters;

import sample.Core.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by kondrashov on 22.08.2016.
 */
public class CanMoveFilter<T extends PlaceHaving> extends AbstractGFilter<T> {
    private static final CanMoveFilter INSTANCE = new CanMoveFilter();
    private MoveAction moveAction;

    private CanMoveFilter() {
    }

    public static CanMoveFilter getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isOk(PlaceHaving obj) {
        return moveAction.canGetCell((GUnit) getObj(), (GameCell) obj);
    }

    @Override
    public void filter(Collection<? extends T> collection) {
        Set<GameCell> gameCells = new HashSet<>();
        Collection<Way> allWays = moveAction.findAllWays((GUnit) getObj(), moveAction);
        allWays.forEach((o) -> gameCells.add(o.getLastCell()));
        Iterator<? extends PlaceHaving> iterator = collection.iterator();
        while (iterator.hasNext()) {
            PlaceHaving next = iterator.next();
            if (!gameCells.contains(next)) {
                iterator.remove();
            }
        }
    }

    public CanMoveFilter setMoveAction(MoveAction moveAction) {
        this.moveAction = moveAction;
        return this;
    }
}
