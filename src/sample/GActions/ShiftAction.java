package sample.GActions;

import sample.Core.AimType;
import sample.Core.GObject;
import sample.Core.GameCell;
import sample.Filters.FilterHelper;

public class ShiftAction extends AbstractGAction {
    public ShiftAction() {
        aimType = AimType.ObjectAndCells;
        filters.add(FilterHelper.object());
        filters.add(FilterHelper.gameCell());
    }

    @Override
    public void doAction() {
        GObject aim = (GObject) getAim();
        GameCell place = (GameCell) getAims().get(1);
        aim.shift(place);
        aims.clear();
    }
}
