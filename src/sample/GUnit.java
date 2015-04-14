package sample;

import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;
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
    private AttackStyle attackStyle;
    private static ResourceBundle bundle = ResourceBundle.getBundle(MyConst.RESOURCE_BUNDLE_LOCATION + "unitTypes");

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
                obj.takeHit(Hit.createHit(this, obj, 1, 0, DamageType.PHYSICAL));
                this.takeHit(Hit.createHit(obj, this, 1, 0, DamageType.PHYSICAL));
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
        attackStyle = AttackStyle.DEFAULT;
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
        final int damage = hit.getDamage();
        if (damage > 0) {
            this.hp -= damage;
            getVisualizer().changeHP(hp);
            GameModel.MODEL.log("base", "HpLeft", this, hp);
            if (hp <= 0) {
                die();
                GameModel.MODEL.log("base", "Dies", this);
            }
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

    public Set<GameCell> getCellsToGo() {
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

    public GUnit copy() {
        GUnit copy = new GUnit(maxHp, maxMp, minDamage, randDamage);
        copy.getMods().clear();
        copy.getMods().addAll(getMods());
        copy.getSkills().clear();
        copy.getSkills().addAll(getSkills());
        copy.setPlayer(player);
        return copy;
    }

    private class BaseUnitAction extends AbstractGAction {
        @Override
        public void act(Selectable obj) {
            if (obj instanceof GObject) {
                GObject gObject = (GObject) obj;
                if (isFriendly(gObject)) {
                    GameModel.SELECT_ACTION.act(gObject);
                } else {
                    attack(gObject);
                    GameModel.MODEL.setLastActedUnit(GUnit.this);
                }
            } else if (obj instanceof GameCell) {
                GameCell gameCell = (GameCell) obj;
                go(gameCell);
                GameModel.MODEL.setLastActedUnit(GUnit.this);
            }
        }
    }

    @Override
    public void startHour() {
        super.startHour();
        this.mp = maxMp;
        visualizer.setReady(true);
    }

    @Override
    public boolean blocksMoveFor(GUnit unit) {
        return getPlayer().isOwnerFor(unit);
    }

    @Override
    public void endTurn() {
        super.endTurn();
        this.mp = 0;
        visualizer.setReady(false);
    }

    @Override
    public String getName() {
        return bundle.getString(getType().toString());
    }

    private void go(GameCell gameCell) {
        moveType.go(this, gameCell);
        if (canAct()) {
            Set<GameCell> cells = getCellsToGo();
            GameModel.MODEL.showSelectionPossibility(cells);
        }
    }

    private void attack(GObject obj) {
        attackStyle.attack(this, obj);
    }
}
