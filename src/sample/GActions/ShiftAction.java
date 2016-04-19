package sample.GActions;

import sample.AimType;
import sample.Filters.FilterHelper;
import sample.GObject;
import sample.GameCell;

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
