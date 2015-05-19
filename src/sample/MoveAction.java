package sample;

import java.util.Set;

public interface MoveAction {
    DefaultMoveAction DEFAULT = new DefaultMoveAction();

    Set<Way> getWayFromCell(Way start, GUnit unit);

    void go(GUnit unit, GameCell gameCell);
}
