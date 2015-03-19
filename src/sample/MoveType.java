package sample;

import java.util.Set;

/**
 * Created by kondrashov on 18.03.2015.
 */
public interface MoveType {
    MoveType DEFAULT = new DefaultMoveType();

    Set<Way> getWayPoints(Way start, GUnit unit);

    void go(GUnit unit, GameCell gameCell);
}
