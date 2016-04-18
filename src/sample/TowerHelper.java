package sample;


import sample.Filters.FilterFactory;

import java.util.List;

public class TowerHelper {
    public static MainTower getPlayersMainTower(Player player) {
        final List<GObject> objects = GameModel.MODEL.getObjects(
                FilterFactory.getFilter(FilterFactory.FilterType.CLASS_FILTER, null, MainTower.class),
                FilterFactory.getFilter(FilterFactory.FilterType.BELONG_TO_PLAYER)
        );
        return (MainTower) objects.iterator().next();
    }
}
