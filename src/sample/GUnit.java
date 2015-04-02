package sample;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GUnit extends GObject {
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int minDamage;
    private int randDamage;
    protected UnitType type;

    private MoveType moveType;
    private AttackType attackType;

    @Override
    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public void push(Direction direction) {
        super.push(direction);
        final GameCell nextCell = GameModel.MODEL.getNextCell(place, direction);
        if (nextCell != null) {
            final GObject obj = nextCell.getObj();
            if (obj == null) {
                shift(nextCell);
            } else {
                obj.takeHit(Hit.createHit(this, obj, 1));
                this.takeHit(Hit.createHit(obj, this, 1));
            }
        }
    }

    public GUnit(int maxHp, int maxMp, int minDamage, int randDamage) {
        this.maxHp = maxHp;
        this.maxMp = maxMp;
        this.minDamage = minDamage;
        this.randDamage = randDamage;
        baseAction = new BaseUnitAction();
        baseAction.setOwner(this);
        moveType = MoveType.DEFAULT;
        attackType = AttackType.DEFAULT;
        skills.add(new EndTurnAction());
        fill();
    }

    private void fill() {
        hp = maxHp;
        mp = maxMp;
    }

    @Override
    public void takeHit(Hit hit) {
        super.takeHit(hit);
        this.hp -= hit.getDamage();
        GameModel.MODEL.log(String.format("%s take %s hit with power = %s", this, hit.getDamageType(), hit.getDamage()));
        GameModel.MODEL.log(this + " has " + hp + " hp left");
        if (hp <= 0) {
            die();
            GameModel.MODEL.log(this + " die!");
        }
    }

    @Override
    public boolean canAct() {
        return hp > 0 && mp > 0;
    }

    @Override
    public String toString() {
        return type.toString() + getXy().toString();
    }

    @Override
    public void select() {
        super.select();
        Set<GameCell> cells = getCellsToGo();
        GameModel.MODEL.showSelectionPossibility(cells);
    }

    private Set<GameCell> getCellsToGo() {
        Set<GameCell> possibleCells = new HashSet<GameCell>();
        final Collection<Way> allWays = GameModel.MODEL.findAllWays(this, moveType);
        for (Way way : allWays) {
            possibleCells.add(way.getCell());
        }
        return possibleCells;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public UnitType getType() {
        return type;
    }

    public int getMP() {
        return mp;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getRandDamage() {
        return randDamage;
    }

    public void looseMP(int k) {
        mp -= k;
    }

    public Integer getControlPower() {
        return 1;
    }

    public int getMaxMP() {
        return maxMp;
    }

    public int getHP() {
        return hp;
    }

    public int getMaxHP() {
        return maxHp;
    }

    private class BaseUnitAction extends Skill {
        @Override
        public void act(Selectable obj) {
            endsTurn = false;
            if (obj instanceof GObject) {
                GObject gObject = (GObject) obj;
                if (isFriendly(gObject)) {
                    GameModel.DefaultAction.act(gObject);
                } else {
                    attack(gObject);
                }
            } else if (obj instanceof GameCell) {
                GameCell gameCell = (GameCell) obj;
                go(gameCell);
            }
        }
    }

    @Override
    public void endHour() {
        super.endHour();
        this.mp = maxMp;
        visualizer.setReady(true);
    }

    @Override
    public void endTurn() {
        super.endTurn();
        this.mp = 0;
        visualizer.setReady(false);
    }

    private void go(GameCell gameCell) {
        moveType.go(this, gameCell);
        if (canAct()) {
            select();
        }
    }

    private void attack(GObject obj) {
        attackType.attack(this, obj);
    }
}
