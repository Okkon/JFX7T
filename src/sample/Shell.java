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
    protected boolean stopped;
    protected DamageType damageType;
    protected ShellVisualizer visualizer;

    public Shell() {
        this.coveredDistance = 0;
        this.stopped = false;
        damageType = DamageType.PHYSICAL;
    }

    public void fire() {
        if (visualizer != null) {
            visualizer.create(cell);
            visualizer.step(cell, GameModel.MODEL.getNextCell(cell, direction));
        }
        while (!stopped) {
            step();
        }
    }

    private void step() {
        final GameCell nextCell = GameModel.MODEL.getNextCell(cell, direction);
        int stepPrice = direction.isDiagonal() ? XY.diagonalLength : XY.straightLength;
        if (nextCell != null && coveredDistance + stepPrice <= maxDistance) {
            coveredDistance += stepPrice;
            GameModel.MODEL.log(name + " moves from " + cell.getXy() + " to " + nextCell.getXy());
            /*if (visualizer != null) {
                visualizer.step(cell, nextCell);
            }*/
            cell = nextCell;
            final GObject obj = cell.getObj();
            if (obj != null) {
                GameModel.MODEL.log(String.format("%s hits %s!", name, obj));
                bumpInto(obj);
            }
        } else {
            stopped = true;
        }
    }

    private void bumpInto(GObject obj) {
        hit(obj);
        stopCheck(obj);
    }

    public void stopCheck(GObject obj) {
        stopped = true;
    }

    public void hit(GObject obj) {
        final Hit hit = Hit.createHit(attacker, obj, minDamage, maxDamage, damageType);
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

    public void setCell(GameCell cell) {
        this.cell = cell;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public void setVisualizer(ShellVisualizer visualizer) {
        this.visualizer = visualizer;
    }
}
