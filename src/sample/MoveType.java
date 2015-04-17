package sample;

import java.util.Set;

public interface MoveType {
    DefaultMoveType DEFAULT = new DefaultMoveType();

    Set<Way> getWayFromCell(Way start, GUnit unit);

    void go(GUnit unit, GameCell gameCell);
}
