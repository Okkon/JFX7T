package sample;


import sample.Filters.AbstractGFilter;
import sample.Filters.FilterFactory;
import sample.Filters.GFilter;

import java.util.List;

public class TowerHelper {
    public static MainTower getPlayersMainTower(Player player) {
        final List<GObject> objects = GameModel.MODEL.getObjects(
                FilterFactory.getFilter(FilterFactory.FilterType.CLASS_FILTER, null, MainTower.class),
                FilterFactory.getFilter(FilterFactory.FilterType.BELONG_TO_PLAYER)
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
