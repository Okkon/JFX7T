package sample;

import static sample.FilterFactory.FilterType.*;


public abstract class ShotAction extends Skill {
    protected int minDamage;
    protected int maxDamage;
    protected int distance;

    @Override
    protected void initialize() {
        super.initialize();
        ownerFilters.add(FilterFactory.getFilter(NOT_IN_DANGER, "InDanger"));
        aimFilters.add(FilterFactory.getFilter(IS_UNIT, "NotUnit"));
        aimFilters.add(FilterFactory.getFilter(NOT_ME, "NotMe"));
        aimFilters.add(FilterFactory.getFilter(IS_ON_ONE_LINE, "NotOnOneLine"));
        aimFilters.add(FilterFactory.getFilter(CAN_SEE, "CantSee"));
        aimFilters.add(FilterFactory.getFilter(OBSTACLE_ON_ONE_LINE, "ObstacleOnLine"));
        final FilterFactory.DistanceFilter filter = (FilterFactory.DistanceFilter) FilterFactory.getFilter(DISTANCE_CHECK, "AimIsTooFar");
        filter.setDistance(distance);
        aimFilters.add(filter);
    }

    public ShotAction(int distance, int minDamage, int maxDamage) {
        this.distance = distance;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        initialize();
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
