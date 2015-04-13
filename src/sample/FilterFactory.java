package sample;

public class FilterFactory {
    private static final GameModel model = GameModel.MODEL;

    public static GFilter getFilter(FilterType type, GObject obj, String error) {
        GFilter gFilter = null;
        switch (type) {
            case IS_UNIT:
                gFilter = new UnitFilter();
                break;
            case IS_NEAR:
                gFilter = new IsNearFilter();
                break;
            case CAN_SEE:
                gFilter = new CanSeeFilter();
                break;
            case CAN_ACT:
                gFilter = new CanActFilter();
                break;
            case IS_ON_ONE_LINE:
                gFilter = new OneLineFilter();
                break;
            case DISTANCE_CHECK:
                gFilter = new DistanceFilter();
                break;
            case OBSTACLE_ON_ONE_LINE:
                gFilter = new ObstacleOnLineFilter();
                break;
            case BELONG_TO_PLAYER:
                gFilter = new BelongToPlayerFilter();
                break;
            case NOT_ME:
                gFilter = new NotMeFilter();
                break;
        }
        if (obj != null) {
            gFilter.setObj(obj);
        }
        gFilter.setType(type);
        gFilter.setErrorText(error);
        return gFilter;
    }

    public static GFilter getFilter(FilterType type) {
        return getFilter(type, null, null);
    }

    public static GFilter getFilter(FilterType type, String error) {
        return getFilter(type, null, error);
    }

    public enum FilterType {
        IS_NEAR, CAN_SEE, CAN_ACT, IS_ON_ONE_LINE, BELONG_TO_PLAYER, IS_UNIT, OBSTACLE_ON_ONE_LINE, DISTANCE_CHECK, NOT_ME
    }

    private static class UnitFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            return obj instanceof GUnit;
        }
    }

    private static class IsNearFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            return model.isNear(getObj(), (GObject) obj);
        }
    }

    private static class CanSeeFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            return model.canSee(getObj(), (GObject) obj);
        }
    }

    public static class DistanceFilter extends AbstractGFilter {
        private int distance;

        public void setDistance(int distance) {
            this.distance = distance;
        }

        @Override
        public boolean isOk(Selectable obj) {
            return XY.getDistance(getObj().getXy(), ((PlaceHaving) obj).getXy()) <= distance;
        }
    }

    private static class CanActFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            return ((GUnit) obj).canAct();
        }
    }

    private static class OneLineFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            return model.onOneLine(getObj(), (GObject) obj);
        }
    }

    private static class ObstacleOnLineFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            return !model.seesObstacle(getObj(), (GObject) obj);
        }
    }

    private static class BelongToPlayerFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            return model.getActivePlayer().equals(((GObject) obj).getPlayer());
        }
    }

    private static class NotMeFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            return !getObj().equals(obj);
        }
    }
}
