package sample.GActions;

import sample.*;

public class KillAction extends AbstractGAction {
    @Override
    public void onSelect() {
        aimType = AimType.Object;
        aimFilters.clear();
        super.onSelect();
    }

    @Override
    public void act(Selectable aim) {
        GObject gObject;
        if (aim instanceof GameCell) {
            GameCell cell = (GameCell) aim;
            gObject = cell.getObj();
            gObject.die(new Hit());
        }
        if (aim instanceof GObject) {
            gObject = (GObject) aim;
            gObject.die(new Hit());
        }
    }

}
