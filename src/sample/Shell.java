package sample;

public class Shell {
    protected GameCell cell;
    protected Direction direction;
    protected int minDamage;
    protected int maxDamage;
    protected int coveredDistance;
    protected int maxDistance;
    protected GUnit attacker;
    protected String name;
    private boolean stoppable;
    private boolean stopped;

    public Shell() {
        this.coveredDistance = 0;
        this.stoppable = true;
        this.stopped = false;
        cell = attacker.getPlace();
    }

    public void fire() {
        while (!stopped) {
            step();
        }
    }

    private void step() {
        final GameCell nextCell = GameModel.MODEL.getNextCell(cell, direction);
        int stepPrice = direction.isDiagonal() ? XY.diagonalLength : XY.straightLength;
        if (nextCell != null && coveredDistance + stepPrice < maxDistance) {
            coveredDistance += stepPrice;
            GameModel.MODEL.log(name + " moves from " + cell.getXy() + " to " + nextCell.getXy());
            cell = nextCell;
            final GObject obj = cell.getObj();
            if (obj != null) {
                bumpInto(obj);
                stopped = stoppable;
            }
        } else {
            stopped = true;
        }
    }

    private void bumpInto(GObject obj) {
        final Hit hit = Hit.createHit(attacker, obj, minDamage, maxDamage);
        obj.takeHit(hit);
    }


    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setAttacker(GObject attacker) {
        this.attacker = (GUnit) attacker;
    }

    public GUnit getAttacker() {
        return attacker;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
