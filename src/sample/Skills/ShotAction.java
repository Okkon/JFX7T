package sample.Skills;

import sample.*;
import sample.Filters.FilterFactory;

import static sample.Filters.FilterFactory.FilterType.*;


public abstract class ShotAction extends Skill {
    protected int minDamage;
    protected int maxDamage;
    protected int distance;

    public ShotAction(int distance, int minDamage, int maxDamage) {
        this.distance = distance;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        ownerFilters.add(new FilterFactory.NotInDangerFilter().setError("InDanger"));
        addAimFilter(IS_UNIT, "NotUnit");
        addAimFilter(NOT_ME, "NotMe");
        addAimFilter(IS_ON_ONE_LINE, "NotOnOneLine");
        addAimFilter(CAN_SEE, "CantSee");
        addAimFilter(OBSTACLE_ON_ONE_LINE, "ObstacleOnLine");
        addAimFilter(DISTANCE_CHECK, "AimIsTooFar", distance);
        aimType = AimType.Object;
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
    public void doAction() {
        Shell shell = createShell();
        aimAt(shell, getAim());
        configureShell(shell);
        shell.fire();
    }

    protected abstract void configureShell(Shell shell);
}
