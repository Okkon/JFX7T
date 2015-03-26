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
        aimFilters.add(FilterFactory.getFilter(IS_UNIT));
        aimFilters.add(FilterFactory.getFilter(CAN_SEE));
        aimFilters.add(FilterFactory.getFilter(IS_ON_ONE_LINE));
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


    protected Shell createShell() {
        final Shell shell = new Shell();
        shell.setMinDamage(getMinDamage());
        shell.setMaxDamage(getMaxDamage());
        shell.setMaxDistance(getDistance());
        shell.setAttacker(getOwner());
        shell.setCell(getOwner().getPlace());
        return shell;
    }

    protected void aimAt(Shell shell, Selectable obj) {
        GUnit aim = ((GUnit) obj);
        final Direction direction = Direction.findDirection(getOwner().getXy(), aim.getXy());
        shell.setDirection(direction);
    }

    @Override
    public void act(Selectable obj) {
        Shell shell = createShell();
        aimAt(shell, obj);
        configureShell(shell);
        shell.fire();
    }

    protected abstract void configureShell(Shell shell);
}
