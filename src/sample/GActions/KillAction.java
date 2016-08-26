package sample.GActions;

import sample.Core.AimType;
import sample.Core.GObject;
import sample.Core.GUnit;
import sample.Events.UnitDeathEvent;
import sample.Filters.FilterFactory;

public class KillAction extends AbstractGAction {
    public KillAction() {
        aimType = AimType.Object;
        addAimFilter(FilterFactory.ClassFilter.newInstance().setClass(GObject.class));
    }

    @Override
    public void doAction() {
        GObject gObject;
        if (getAim() instanceof GObject) {
            gObject = (GObject) getAim();
            if (gObject instanceof GUnit) {
                GUnit unit = (GUnit) gObject;
                new UnitDeathEvent(unit).process();
            }
            gObject.die(null);
        }
    }

}
