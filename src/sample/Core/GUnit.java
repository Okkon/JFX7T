package sample.Core;

import sample.Direction;
import sample.Events.ShiftEvent;
import sample.Events.UnitEndTurnEvent;
import sample.Filters.FilterFactory;
import sample.Filters.IsFriendlyFilter;
import sample.Filters.IsNearFilter;
import sample.GActions.AbstractGAction;
import sample.GActions.AttackAction;
import sample.Helpers.NameHelper;
import sample.MyConst;
import sample.Skills.EndTurnAction;
import sample.Skills.ShotAction;
import sample.Skills.TeleportToTower;
import sample.Tower.MainTower;
import sample.Tower.TowerHelper;
import sample.XY;

import java.util.*;

public class GUnit extends GObject {
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int minDamage;
    private int randDamage;

    @Override
    public String getDescription() {
        return NameHelper.getName("unitDescription", getType());
    }

    protected String type;

    private DefaultMoveAction moveType;
    private AttackAction attackAction;

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
                ShiftEvent shiftEvent = new ShiftEvent(this, nextCell);
                shiftEvent.process();
            } else {
                obj.takeHit(Hit.createHit(this, obj, 1, 0, DamageType.PHYSICAL));
                this.takeHit(Hit.createHit(obj, this, 1, 0, DamageType.PHYSICAL));
            }
        }
    }

    public GUnit(int maxHp, int maxMp, int minDamage, int randDamage) {
        setStats(maxHp, maxMp, minDamage, randDamage);
        baseAction = new BaseUnitAction();
        baseAction.setOwner(this);
        moveType = MoveAction.DEFAULT;
        attackAction = AttackAction.getInstance();
        skills.add(attackAction);
        skills.add(new EndTurnAction());
    }

    private Skill findRangeAttack() {
        for (GAction skill : skills) {
            if (skill instanceof ShotAction) {
                return (ShotAction) skill;
            }
        }
        return null;
    }

    private void setStats(int maxHp, int maxMp, int minDamage, int randDamage) {
        this.maxHp = maxHp;
        this.maxMp = maxMp;
        this.minDamage = minDamage;
        this.randDamage = randDamage;
        fill();
    }

    private void fill() {
        hp = maxHp;
        mp = maxMp;
    }

    @Override
    public void die(Hit hit) {
        super.die(hit);
        final GObject attacker = hit.getAttacker();
        if (attacker != null && attacker.getPlayer() != null) {
            final Player attackerPlayer = attacker.getPlayer();
            if (!attackerPlayer.equals(Player.NEUTRAL)) {
                int score = MyConst.SCORE_FOR_UNIT;
                attackerPlayer.score(attackerPlayer.isOwnerFor(this) ? -score : score);
            }
        }
    }

    @Override
    public int takeHit(Hit hit) {
        super.takeHit(hit);
        final int damage = hit.getDamage();
        if (damage > 0) {
            int takenDamage = Math.min(hp, damage);
            this.hp -= takenDamage;
            getVisualizer().changeHP(hp);
            if (hp <= 0) {
                die(hit);
                GameModel.MODEL.log("base", "Dies", this);
            } else {
                GameModel.MODEL.log("base", "HpLeft", this, hp);
            }
        }
        return damage;
    }

    @Override
    public boolean canAct() {
        return hp > 0 && mp > 0;
    }

    @Override
    public String toString() {
        return type.toString() + (place != null ? " " + place.getXy().toString() : "");
    }

    public Set<GameCell> getCellsToGo() {
        Set<GameCell> possibleCells = new HashSet<>();
        final Collection<Way> allWays = GameModel.MODEL.findAllWays(this, moveType);
        for (Way way : allWays) {
            possibleCells.add(way.getDestinationCell());
        }
        return possibleCells;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
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
        copy.mods.clear();
        copy.mods.addAll(mods);
        copy.getSkills().clear();
        copy.getSkills().addAll(getSkills());
        copy.setPlayer(getPlayer());
        copy.setType(getType());
        return copy;
    }

    public int recover(int value) {
        int healedHp = Math.min(value, maxHp - hp);
        if (healedHp > 0) {
            hp += healedHp;
            getVisualizer().changeHP(hp);
            GameModel.MODEL.log("base", "Recover", this, healedHp);
        }
        return healedHp;
    }

    public boolean isWounded() {
        return hp < maxHp;
    }

    public List<? extends GAction> getExtraSkills() {
        List<Skill> list = new ArrayList<>();
        if (getPlace() == null) {
            return list;
        }
        if (!hasActed() && isNearToMainTower()) {
            list.add(new TeleportToTower());
        }
        return list;
    }

    private boolean isNearToMainTower() {
        final Collection<GFilter> filters = new ArrayList<>();
        filters.add(new IsNearFilter());
        filters.add(new IsFriendlyFilter());
        filters.add(FilterFactory.ClassFilter.newInstance().setClass(MainTower.class));
        for (GFilter filter : filters) {
            filter.setObj(this);
        }
        return !GameModel.MODEL.getObjects(filters).isEmpty();
    }

    private boolean hasActed() {
        return maxMp != mp;
    }

    public void setMP(int MP) {
        this.mp = MP;
    }

    public int estimate(GameCell cell) {
        final MainTower mainTower = TowerHelper.getPlayersMainTower(getPlayer());
        return Math.abs(cell.getXy().getX() - mainTower.getXy().getX());
    }

    public void setAttackAction(AttackAction attackAction) {
        this.attackAction = attackAction;
    }

    private class BaseUnitAction extends AbstractGAction {
        AbstractGAction skill;

        @Override
        public boolean canSelect(PlaceHaving obj) {
            if (obj instanceof GObject) {
                GObject gObject = (GObject) obj;
                if (isFriendlyFor(gObject)) {
                    skill = GameModel.SELECT_ACTION;
                } else if (XY.isNear(getXy(), obj.getXy())) {
                    skill = attackAction;
                } else {
                    final Skill rangeAttack = findRangeAttack();
                    skill = rangeAttack == null ? attackAction : rangeAttack;
                }
            } else if (obj instanceof GameCell) {
                skill = moveType;
            }

            skill.setOwner(GUnit.this);
            for (GFilter filter : skill.getAimFilters()) {
                filter.setObj(getOwner());
                if (!filter.check(obj)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public void onSelect() {
            if (aims.size() > 0) {
                skill.getAims().add(this.getAim());
                this.aims.clear();
                skill.perform();
            } else {
                Set<GameCell> cells = getCellsToGo();
                cells.add(getPlace());
                GameModel.MODEL.showSelectionPossibility(cells);
            }
        }

        @Override
        protected boolean needsLogging() {
            return false;
        }

        @Override
        public void doAction() {

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
        return !getPlayer().isOwnerFor(unit);
    }

    @Override
    public void endTurn() {
        super.endTurn();
        new UnitEndTurnEvent(this).process();
    }

    @Override
    public String getName() {
        return NameHelper.getName("unitTypes", getType().toString());
    }

    public void go(GameCell gameCell) {
        moveType.setOwner(this);
        moveType.tryToSelect(gameCell);
    }

}
