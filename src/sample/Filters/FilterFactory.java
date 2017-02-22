package sample.Filters;

import sample.Core.GFilter;
import sample.Core.GObject;
import sample.Core.GameModel;
import sample.Core.PlaceHaving;
import sample.XY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilterFactory {
    private static final GameModel model = GameModel.MODEL;

    public static GFilter getFilter(FilterType type, String error, Object... params) {
        GFilter gFilter = null;
        switch (type) {
            case IS_ON_ONE_LINE:
                gFilter = new OneLineFilter();
                break;
            case DISTANCE_CHECK:
                gFilter = new DistanceFilter().setDistance((Integer) params[0]);
                break;
            case CLASS_FILTER:
                gFilter = new ClassFilter().setClass((Class) params[0]);
                break;
            case OBSTACLE_ON_ONE_LINE:
                gFilter = new ObstacleOnLineFilter();
                break;
            case NOT_IN_DANGER:
                gFilter = new NotInDangerFilter();
                break;
            case NOT_ME:
                gFilter = new NotMeFilter();
                break;
        }
        if (error != null) {
            gFilter.setError(error);
        }
        return gFilter;
    }

    public static GFilter getFilter(FilterType type) {
        return getFilter(type, null);
    }

    public static Collection<GFilter> getFilters(FilterType... types) {
        List<GFilter> gFilters = new ArrayList<>();
        for (FilterType type : types) {
            gFilters.add(getFilter(type));
        }
        return gFilters;
    }

    public enum FilterType {
        IS_ON_ONE_LINE, OBSTACLE_ON_ONE_LINE, DISTANCE_CHECK, NOT_IN_DANGER, CLASS_FILTER, NOT_ME
    }

    public static class ClassFilter extends AbstractGFilter {
        private Class clazz;

        public ClassFilter setClass(Class clazz) {
            this.clazz = clazz;
            return ClassFilter.this;
        }

        @Override
        public boolean isOk(PlaceHaving obj) {
            return clazz.isInstance(obj);
        }

        public static ClassFilter newInstance() {
            return new ClassFilter();
        }
    }

    private static class DistanceFilter extends AbstractGFilter {
        private int distance;

        public DistanceFilter setDistance(int distance) {
            this.distance = distance;
            return DistanceFilter.this;
        }

        @Override
        public boolean isOk(PlaceHaving obj) {
            return XY.getDistance(getObj().getXy(), obj.getXy()) <= distance;
        }
    }

    private static class OneLineFilter extends AbstractGFilter {
        @Override
        public boolean isOk(PlaceHaving obj) {
            return model.onOneLine(getObj(), (GObject) obj);
        }
    }

    private static class ObstacleOnLineFilter extends AbstractGFilter {
        @Override
        public boolean isOk(PlaceHaving obj) {
            return !model.seesObstacle(getObj(), (GObject) obj);
        }
    }

    private static class NotMeFilter extends AbstractGFilter {
        @Override
        public boolean isOk(PlaceHaving obj) {
            return !getObj().equals(obj);
        }
    }

    public static class NotInDangerFilter extends AbstractGFilter {
        @Override
        public boolean isOk(PlaceHaving obj) {
            return !GameModel.MODEL.isInDanger(obj);
        }
    }
}
