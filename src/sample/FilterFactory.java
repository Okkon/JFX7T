package sample;

import java.util.HashMap;

public class FilterFactory {
    private static final GameModel model = GameModel.MODEL;
    private static HashMap<FilterType, GFilter> hashMap = new HashMap<FilterType, GFilter>();

    public static GFilter getFilter(FilterType type, GObject obj) {
        GFilter gFilter = hashMap.get(type);
        if (gFilter != null) {
            return gFilter;
        } else switch (type) {
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
            case IS_NOT_ENEMY:
                gFilter = new NotEnemyFilter();
                break;
            case BELONG_TO_PLAYER:
                gFilter = new BelongToPlayerFilter();
                break;
        }
        if (obj != null) {
            gFilter.setObj(obj);
        }
        return gFilter;
    }

    public static GFilter getFilter(FilterType type) {
        return getFilter(type, null);
    }

    public enum FilterType {
        IS_NEAR, CAN_SEE, CAN_ACT, IS_ON_ONE_LINE, IS_NOT_ENEMY, BELONG_TO_PLAYER, IS_UNIT
    }

    private static class UnitFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            final boolean b = obj instanceof GUnit;
            if (!b) {
                model.error("You must select unit!");
            }
            return b;
        }
    }

    private static class IsNearFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            final boolean b = model.isNear(getObj(), (GObject) obj);
            if (!b) {
                model.error("Aim is not b!");
            }
            return b;
        }
    }

    private static class CanSeeFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            final boolean b = model.canSee(getObj(), (GObject) obj);
            if (!b) {
                model.error("Selected unit don't see the aim!");
            }
            return b;
        }
    }

    private static class CanActFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            final boolean b = ((GUnit) obj).canAct();
            if (!b) {
                model.error("Selected unit can't act!");
            }
            return b;
        }
    }

    private static class NotEnemyFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            final boolean b = model.isNotEnemy(getObj(), (GObject) obj);
            if (!b) {
                model.error("Selected unit is enemy!");
            }
            return b;
        }
    }

    private static class OneLineFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            final boolean b = model.onOneLine(getObj(), (GObject) obj);
            if (!b) {
                model.error("Aim is not on the same line!");
            }
            return b;
        }
    }

    private static class BelongToPlayerFilter extends AbstractGFilter {
        @Override
        public boolean isOk(Selectable obj) {
            final boolean b = model.getActivePlayer().equals(((GObject) obj).getPlayer());
            if (!b) {
                model.error("Can't choose action of other player's unit!");
            }
            return b;
        }
    }
}