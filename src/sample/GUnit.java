package sample;

import sample.Events.UnitEndTurnEvent;
import sample.GActions.AbstractGAction;
import sample.Skills.EndTurnAction;
import sample.Skills.TeleportToTower;

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
        return NameHelper.getName("unitDescription", getType().name());
    }

    protected UnitType type;

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
                shift(nextCell);
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
        attackAction = AttackAction.DEFAULT;
        skills.add(attackAction);
        skills.add(new EndTurnAction());
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
        Set<GameCell> possibleCells = new HashSet<GameCell>();
        final Collection<Way> allWays = GameModel.MODEL.findAllWays(this, moveType);
        for (Way way : allWays) {
            possibleCells.add(way.getDestinationCell());
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
        copy.setPlayer(getPlayer());
        copy.setType(getType());
        return copy;
    }

    public int recover(int value) {
        int healedHp = Math.min(value, maxHp - hp);
        if (healedHp > 0) {
            hp += healedHp;
            getVisualizer().changeHP(hp);
            GameModel.MODEL.log("base", "Recover", this, hp);
        }
        return healedHp;
    }

    public boolean isWounded() {
        return hp < maxHp;
    }

    public List<? extends GAction> getExtraSkills() {
        List<Skill> list = new ArrayList<Skill>();
        if (getPlace() == null) {
            return list;
        }
        if (!hasActed() && isNearToMainTower()) {
            list.add(new TeleportToTower());
        }
        return list;
    }

    private boolean isNearToMainTower() {
        final Collection<GFilter> filters = FilterFactory.getFilters(FilterFactory.FilterType.IS_NEAR, FilterFactory.FilterType.IS_FRIENDLY);
        filters.add(FilterFactory.getFilter(FilterFactory.FilterType.CLASS_FILTER, null, MainTower.class));
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

    private class BaseUnitAction extends AbstractGAction {
        @Override
        public void act(Selectable obj) {
            if (obj instanceof GObject) {
                GObject gObject = (GObject) obj;
                if (isFriendlyFor(gObject)) {
                    GameModel.SELECT_ACTION.act(gObject);
                } else {
                    attack(gObject);
                }
            } else if (obj instanceof GameCell) {
                GameCell gameCell = (GameCell) obj;
                go(gameCell);
            }
        }

        @Override
        public void onSelect() {
            super.onSelect();
            Set<GameCell> cells = getCellsToGo();
            cells.add(getPlace());
            GameModel.MODEL.showSelectionPossibility(cells);
        }

        @Override
        protected boolean needsLogging() {
            return false;
        }

        @Override
        protected void afterPerform() {
            //do nothing
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
        moveType.perform(gameCell);
    }

    private void attack(GObject obj) {
        attackAction.setOwner(this);
        attackAction.perform(obj);
    }
}
