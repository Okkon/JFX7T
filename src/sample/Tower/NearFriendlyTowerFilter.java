package sample.Tower;

import sample.Core.GFilter;
import sample.Core.GObject;
import sample.Core.GameCell;
import sample.Core.PlaceHaving;
import sample.Filters.AbstractGFilter;
import sample.Filters.BelongsToActivePlayerFilter;
import sample.Filters.FilterFactory;

import java.util.*;

/**
 * Created by kondrashov on 22.08.2016.
 */
public class NearFriendlyTowerFilter<T extends PlaceHaving> extends AbstractGFilter<T> {
    private static final NearFriendlyTowerFilter INSTANCE = new NearFriendlyTowerFilter();
    private List<GObject> towers = new ArrayList<>();

    private NearFriendlyTowerFilter() {
    }

    public static NearFriendlyTowerFilter getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isOk(PlaceHaving obj) {
        towers = getTowers();
        for (GObject tower : towers) {
            if (model.isNear(tower, obj)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void filter(Collection<? extends T> collection) {
        towers = getTowers();
        Set<GameCell> gameCells = new HashSet<>();
        towers.forEach(o -> gameCells.addAll(model.getBoard().getNearCells(o.getPlace())));

        Iterator<? extends PlaceHaving> iterator = collection.iterator();
        while (iterator.hasNext()) {
            PlaceHaving next = iterator.next();
            if (!gameCells.contains(next)) {
                iterator.remove();
            }
        }
    }

    private List<GObject> getTowers() {
        final Collection<GFilter> filters = FilterFactory.getFilters(FilterFactory.FilterType.NOT_IN_DANGER);
        filters.add(FilterFactory.getFilter(FilterFactory.FilterType.CLASS_FILTER, null, Tower.class));
        filters.add(new BelongsToActivePlayerFilter());
        return model.getObjects(filters);
    }
}