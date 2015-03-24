package sample;

import static sample.FilterFactory.FilterType.*;

public class Crossbow extends ShotAction {

    public Crossbow(int minDamage, int maxDamage, int distance) {
        super(distance, minDamage, maxDamage);
        aimFilters.add(FilterFactory.getFilter(IS_UNIT));
        aimFilters.add(FilterFactory.getFilter(CAN_SEE, getOwner()));
        aimFilters.add(FilterFactory.getFilter(IS_ON_ONE_LINE, getOwner()));
    }

    @Override
    public void act(Selectable obj) {
        Shell shell = new Shell();
        shell.fire();
    }
}
