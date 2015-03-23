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
    private boolean isAlive;
    private MoveType moveType;
    private AttackType attackType;

    public GUnit(int maxHp, int maxMp, int minDamage, int randDamage) {
        this.maxHp = maxHp;
        this.maxMp = maxMp;
        this.minDamage = minDamage;
        this.randDamage = randDamage;
        isAlive = true;
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
        this.hp -= hit.getDamage();
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
        return type.toString().substring(0,2);
    }

    @Override
    public void select(GAction action) {
        super.select(action);
        Set<GameCell> cells = getCellsToGo();
        GameModel.MODEL.showSelectionPossibility(cells);
    }

    private Set<GameCell> getCellsToGo() {
        Set<GameCell> possibleCells = new HashSet<GameCell>();
        final Collection<Way> allWays = GameModel.findAllWays(this, moveType);
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

    public int getDamage() {
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

    private class BaseUnitAction extends AbstractGAction {
        @Override
        public void act(Selectable obj) {
            if (obj instanceof GObject) {
                GObject gObject = (GObject) obj;
                if (isFriendly(gObject)){
                    DefaultAction.act(gObject);
                }else {
                    attack(gObject);
                }
            }else if (obj instanceof GameCell) {
                GameCell gameCell = (GameCell) obj;
                go(gameCell);
            }
        }
    }

    @Override
    public void endTurn() {
        super.endTurn();
        this.mp = 0;
    }

    private void go(GameCell gameCell) {
        moveType.go(this, gameCell);
        if (canAct()) {
            select(GAction.DefaultAction);
        }
    }

    private void attack(GObject obj) {
        attackType.attack(this, obj);
    }
}
