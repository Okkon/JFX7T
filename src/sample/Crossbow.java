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
        GUnit unit = ((GUnit) obj);
        final Direction direction = Direction.findDirection(getOwner().getXy(), unit.getXy());
        Shell shell = new Shell();
        shell.setMinDamage(getMinDamage());
        shell.setMaxDamage(getMaxDamage());
        shell.setAttacker(getOwner());
        shell.setMaxDistance(getDistance());
        shell.setDirection(direction);
        shell.setName("Arrow");
        shell.fire();
    }

}
