package sample.Core;

import sample.Direction;
import sample.Helpers.NameHelper;
import sample.XY;

public abstract class Shell {
    protected GameCell cell;
    protected Direction direction;
    protected int minDamage;
    protected int randDamage;
    protected int coveredDistance;
    protected int maxDistance;
    protected GObject attacker;
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
            visualizer.create(cell, this);
        }
        while (!stopped) {
            step();
        }
        visualizer.destroy(cell);
    }

    public void step() {
        final GameCell nextCell = GameModel.MODEL.getNextCell(cell, direction);
        int stepPrice = direction.isDiagonal() ? XY.diagonalLength : XY.straightLength;
        if (nextCell != null && coveredDistance + stepPrice <= maxDistance) {
            coveredDistance += stepPrice;
            GameModel.MODEL.log("base", "UnitMove", name, cell.getXy(), nextCell.getXy());
            visualizer.step(cell, nextCell);
            cell = nextCell;
            final GObject obj = nextCell.getObj();
            if (obj != null) {
                GameModel.MODEL.log("base", "Hits", name, obj);
                bumpInto(obj);
            }
        } else {
            stopped = true;
        }
    }

    private void bumpInto(GObject obj) {
        for (GMod mod : obj.getMods()) {
            mod.onTakeShot(this);
        }
        hit(obj);
        stopCheck(obj);
    }

    public void stopCheck(GObject obj) {
        stopped = true;
    }

    public void hit(GObject obj) {
        final Hit hit = Hit.createHit(attacker, obj, minDamage, randDamage, damageType);
        hit.setAttackType(AttackType.RANGE);
        obj.takeHit(hit);
    }


    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public void setRandDamage(int randDamage) {
        this.randDamage = randDamage;
    }

    public int getRandDamage() {
        return randDamage;
    }

    public void setAttacker(GObject attacker) {
        this.attacker = attacker;
    }

    public GObject getAttacker() {
        return attacker;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setName(String name) {
        this.name = NameHelper.getName("shellNames", name);
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

    public Direction getDirection() {
        return direction;
    }

    public int reduceDamage(int value, DamageType damageType) {
        if (this.damageType != damageType) {
            return 0;
        }
        final int shellDamage = minDamage;
        int reducedDamage = Math.min(shellDamage, value);
        minDamage = minDamage - reducedDamage;
        return reducedDamage;
    }
}
