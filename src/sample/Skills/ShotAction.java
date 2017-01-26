package sample.Skills;

import sample.Core.*;
import sample.Direction;
import sample.Filters.FilterFactory;
import sample.Helpers.NameHelper;

import static sample.Filters.FilterFactory.FilterType.*;


public abstract class ShotAction extends Skill {
    protected int minDamage;
    protected int maxDamage;
    protected int distance;

    @Override
    public String getDescription() {
        return NameHelper.getName(
                "skillDescription",
                getClass().getSimpleName(),
                distance,
                minDamage,
                minDamage + maxDamage
        );
    }

    public ShotAction(int distance, int minDamage, int maxDamage) {
        this.distance = distance;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        ownerFilters.add(new FilterFactory.NotInDangerFilter().setError("InDanger"));
        addAimFilter(FilterFactory.ClassFilter.newInstance().setClass(GUnit.class).setError("NotUnit"));
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
        shell.setRandDamage(getMaxDamage());
        shell.setMaxDistance(getDistance());
        shell.setAttacker(getActor());
        shell.setCell(getActor().getPlace());

        final Direction direction = Direction.findDirection(getActor().getXy(), obj.getXy());
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
