package sample.Tower;


import sample.Core.*;
import sample.Filters.AbstractGFilter;
import sample.Filters.BelongsToActivePlayerFilter;
import sample.Filters.FilterFactory;
import sample.XY;

import java.util.List;

public class TowerHelper {
    public static MainTower getPlayersMainTower(Player player) {
        final List<GObject> objects = GameModel.MODEL.getObjects(
                FilterFactory.getFilter(FilterFactory.FilterType.CLASS_FILTER, null, MainTower.class),
                new BelongsToActivePlayerFilter()
        );
        return (MainTower) objects.iterator().next();
    }

    public static GFilter nearMainTowerFilter() {
        return new AbstractGFilter() {
            @Override
            public boolean isOk(PlaceHaving obj) {
                final MainTower tower = getPlayersMainTower(model.getActivePlayer());
                if (tower != null) {
                    return XY.isNear(tower.getXy(), obj.getXy());
                }
                return false;
            }
        };
    }
}
