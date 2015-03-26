package sample;

import static sample.FilterFactory.FilterType.*;

public class Crossbow extends ShotAction {

    public Crossbow(GObject obj, int minDamage, int maxDamage, int distance) {
        super(obj, distance, minDamage, maxDamage);
        aimFilters.add(FilterFactory.getFilter(IS_UNIT));
        aimFilters.add(FilterFactory.getFilter(CAN_SEE, obj));
        aimFilters.add(FilterFactory.getFilter(IS_ON_ONE_LINE, obj));
    }

    @Override
    public void act(Selectable obj) {
        GUnit unit = ((GUnit) obj);
        final Direction direction = Direction.findDirection(getOwner().getXy(), unit.getXy());
        Shell shell = new Shell();
        shell.setMinDamage(getMinDamage());
        shell.setMaxDamage(getMaxDamage());
        shell.setAttacker(getOwner());
        shell.setCell(getOwner().getPlace());
        shell.setMaxDistance(getDistance());
        shell.setDirection(direction);
        shell.setName("Arrow");
        shell.fire();
    }

}
