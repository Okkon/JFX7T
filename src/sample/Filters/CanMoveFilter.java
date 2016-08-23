package sample.Filters;

import sample.Core.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kondrashov on 22.08.2016.
 */
public class CanMoveFilter extends AbstractGFilter {
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
    public Collection<? extends PlaceHaving> filter(Collection<? extends PlaceHaving> objects) {
        Set<GameCell> gameCells = new HashSet<>();
        Collection<Way> allWays = moveAction.findAllWays((GUnit) getObj(), moveAction);
        for (Way way : allWays) {
            gameCells.add(way.getLastCell());
        }
        return super.filter(objects);
    }

    public CanMoveFilter setMoveAction(MoveAction moveAction) {
        this.moveAction = moveAction;
        return this;
    }
}
