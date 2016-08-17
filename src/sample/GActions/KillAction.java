package sample.GActions;

import sample.Core.AimType;
import sample.Core.GObject;
import sample.Core.GameCell;
import sample.Core.Hit;

public class KillAction extends AbstractGAction {
    public KillAction() {
        aimType = AimType.Object;
        getAimFilters().clear();
    }

    @Override
    public void doAction() {
        GObject gObject;
        if (getAim() instanceof GameCell) {
            GameCell cell = (GameCell) getAim();
            gObject = cell.getObj();
            gObject.die(new Hit());
        }
        if (getAim() instanceof GObject) {
            gObject = (GObject) getAim();
            gObject.die(new Hit());
        }
    }

}
