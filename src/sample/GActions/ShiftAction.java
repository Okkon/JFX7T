package sample.GActions;

import sample.*;
import sample.Filters.FilterFactory;

public class ShiftAction extends AbstractGAction {
    public ShiftAction() {
        aimType = AimType.Object;
        aimFilters.clear();
    }

    @Override
    public void act(Selectable aim) {
        if (aim instanceof GameCell) {
            GameCell cell = (GameCell) aim;
            owner.shift(cell);
        }
    }

    @Override
    public boolean canSelect(Selectable obj) {
        if (aimType == AimType.Object && obj instanceof GObject) {
            GObject gObject = (GObject) obj;
            setOwner(gObject);
            aimType = AimType.Cell;
            aimFilters.clear();
            aimFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.IS_VACANT_CELL));
            super.onSelect();
            return false;
        }
        return super.canSelect(obj);
    }
}
