package sample;

import static sample.FilterFactory.FilterType.*;


public abstract class ShotAction extends Skill {
    protected int minDamage;
    protected int maxDamage;
    protected int distance;

    public ShotAction(int distance, int minDamage, int maxDamage) {
        this.distance = distance;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        aimFilters.add(FilterFactory.getFilter(IS_UNIT, "You must select unit!"));
        aimFilters.add(FilterFactory.getFilter(NOT_ME, "Actor can't be the aim!"));
        aimFilters.add(FilterFactory.getFilter(IS_ON_ONE_LINE, "Aim is not on the same line!"));
        aimFilters.add(FilterFactory.getFilter(CAN_SEE, "Selected unit don't see the aim!"));
        aimFilters.add(FilterFactory.getFilter(OBSTACLE_ON_ONE_LINE, "There is an obstacle on the line!"));
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getDistance() {
        return distance;
    }


    protected abstract Shell createShell();

    protected void aimAt(Shell shell, PlaceHaving obj) {
        shell.setMinDamage(getMinDamage());
        shell.setMaxDamage(getMaxDamage());
        shell.setMaxDistance(getDistance());
        shell.setAttacker(getOwner());
        shell.setCell(getOwner().getPlace());

        final Direction direction = Direction.findDirection(getOwner().getXy(), obj.getXy());
        shell.setDirection(direction);
    }

    @Override
    public void act(Selectable obj) {
        Shell shell = createShell();
        aimAt(shell, (PlaceHaving) obj);
        configureShell(shell);
        shell.fire();
    }

    protected abstract void configureShell(Shell shell);
}
