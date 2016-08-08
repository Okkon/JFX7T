package sample.Filters;

import sample.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilterFactory {
    private static final GameModel model = GameModel.MODEL;

    public static GFilter getFilter(FilterType type, String error, Object... params) {
        GFilter gFilter = null;
        switch (type) {
            case IS_UNIT:
                gFilter = new UnitFilter();
                break;
            case CAN_SEE:
                gFilter = new CanSeeFilter();
                break;
            case CAN_BE_ATTACKED:
                gFilter = new CanAttackFilter();
                break;
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
            case IS_NEAR_FRIENDLY_TOWER:
                gFilter = new IsNearFriendlyTower();
                break;
            case BELONG_TO_PLAYER:
                gFilter = new BelongsToActivePlayerFilter();
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
        List<GFilter> gFilters = new ArrayList<GFilter>();
        for (FilterType type : types) {
            gFilters.add(getFilter(type));
        }
        return gFilters;
    }

    public enum FilterType {
        CAN_SEE, IS_ON_ONE_LINE, BELONG_TO_PLAYER, IS_UNIT, OBSTACLE_ON_ONE_LINE, DISTANCE_CHECK, CAN_BE_ATTACKED, NOT_IN_DANGER, CLASS_FILTER, IS_NEAR_FRIENDLY_TOWER, NOT_ME
    }

    public static class UnitFilter extends AbstractGFilter {
        @Override
        public boolean isOk(PlaceHaving obj) {
            return obj instanceof GUnit;
        }
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
    }

    private static class IsNearFriendlyTower extends AbstractGFilter {
        @Override
        public boolean isOk(PlaceHaving obj) {
            final Collection<GFilter> filters = getFilters(FilterType.NOT_IN_DANGER, FilterType.BELONG_TO_PLAYER);
            filters.add(getFilter(FilterType.CLASS_FILTER, null, Tower.class));
            final List<GObject> towers = model.getObjects(filters);
            for (GObject tower : towers) {
                if (model.isNear(tower, (PlaceHaving) obj)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static class CanSeeFilter extends AbstractGFilter {
        @Override
        public boolean isOk(PlaceHaving obj) {
            return model.canSee(getObj(), (GObject) obj);
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
            return XY.getDistance(getObj().getXy(), ((PlaceHaving) obj).getXy()) <= distance;
        }
    }

    private static class CanAttackFilter extends AbstractGFilter {
        @Override
        public boolean isOk(PlaceHaving obj) {
            return GameModel.MODEL.canAttack(getObj(), obj);
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

    private static class BelongsToActivePlayerFilter extends AbstractGFilter {
        private Player player;

        @Override
        public boolean isOk(PlaceHaving obj) {
            return GameModel.MODEL.getActivePlayer().equals(((GObject) obj).getPlayer());
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
