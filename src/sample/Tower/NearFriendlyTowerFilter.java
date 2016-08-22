package sample.Tower;

import sample.Core.GFilter;
import sample.Core.GObject;
import sample.Core.PlaceHaving;
import sample.Filters.AbstractGFilter;
import sample.Filters.FilterFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kondrashov on 22.08.2016.
 */
public class NearFriendlyTowerFilter extends AbstractGFilter {
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
    public Collection<? extends PlaceHaving> filter(Collection<? extends PlaceHaving> objects) {
        towers = getTowers();
        ArrayList<PlaceHaving> result = new ArrayList<>();
        for (PlaceHaving object : objects) {
            for (GObject tower : towers) {
                if (model.isNear(tower, object)) {
                    result.add(object);
                }
            }
        }
        return result;
    }

    private List<GObject> getTowers() {
        final Collection<GFilter> filters = FilterFactory.getFilters(FilterFactory.FilterType.NOT_IN_DANGER, FilterFactory.FilterType.BELONG_TO_PLAYER);
        filters.add(FilterFactory.getFilter(FilterFactory.FilterType.CLASS_FILTER, null, Tower.class));
        return model.getObjects(filters);
    }
}