package sample.GActions;

import sample.Core.AimType;
import sample.Core.GObject;
import sample.Core.GameCell;
import sample.Filters.FilterFactory;
import sample.Filters.VacantCellFilter;

public class ShiftAction extends AbstractGAction {

    @Override
    protected void setAimFilters() {
        clearAimFilters();
        if (aims.isEmpty()) {
            aimType = AimType.Object;
            addAimFilter(FilterFactory.ClassFilter.newInstance().setClass(GObject.class));
        }
        if (!aims.isEmpty()) {
            aimType = AimType.Cell;
            addAimFilter(VacantCellFilter.getInstance());
        }
    }

    private void clearAimFilters() {
        aimFilters.clear();
    }

    @Override
    protected boolean allAimsSelected() {
        return aims.size() == 2;
    }

    @Override
    public void doAction() {
        GObject aim = (GObject) getAim();
        GameCell place = (GameCell) getAims().get(1);
        aim.shift(place);
        aims.clear();
        cancel();
    }
}
