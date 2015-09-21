package sample;

public class ShiftAction extends AbstractGAction {
    @Override
    public void onSelect() {
        aimType = AimType.Object;
        aimFilters.clear();
        super.onSelect();
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
