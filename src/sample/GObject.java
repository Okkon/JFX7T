package sample;

import sample.Events.ShiftEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class GObject implements Selectable, PlaceHaving {
    protected GObjectVisualizer visualizer;
    protected GameCell place;
    protected Player player;
    protected GAction baseAction = GameModel.SELECT_ACTION;
    private List<GMod> mods = new ArrayList<GMod>();
    protected List<GAction> skills = new ArrayList<GAction>();


    public GObject() {
    }

    @Override
    public String toString() {
        return "object" + (place != null ? place.getXy().toString() : "");
    }

    public int takeHit(Hit hit) {
        GameModel.MODEL.log("base", "TakesHit", this, hit.getDamageType(), hit.getDamage());
        for (GMod mod : getMods()) {
            mod.onTakeHit(hit);
        }
        return 0;
    }

    public void die(Hit hit) {
        GameModel.MODEL.getObjects().remove(this);
        place.setObj(null);
        visualizer.die(place);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void shift(GameCell cellToGo) {
        if (cellToGo != null && cellToGo.getObj() == null) {
            final ShiftEvent shiftEvent = new ShiftEvent();
            shiftEvent.setObject(this);
            shiftEvent.setFinishCell(cellToGo);
            shiftEvent.process();
        }
    }

    public boolean isAlive() {
        return true;
    }

    public GAction getBaseAction() {
        return baseAction;
    }

    public boolean isFriendlyFor(GObject gObject) {
        return getPlayer().isOwnerFor(gObject);
    }

    public GameCell getPlace() {
        return place;
    }

    public void setPlace(GameCell place) {
        this.place = place;
    }

    public boolean canAct() {
        return false;
    }

    public void setVisualizer(GObjectVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public XY getXy() {
        return place.getXy();
    }

    public void endTurn() {
        for (GMod mod : mods) {
            mod.onTurnEnd(this);
        }
    }

    public List<GMod> getMods() {
        return mods;
    }

    public void addMod(GMod mod) {
        mods.add(mod);
        final GObjectVisualizer objectVisualizer = getVisualizer();
        if (objectVisualizer != null) {
            mod.applyEffect(this);
        }
    }

    public void addSkill(GAction skill) {
        this.skills.add(0, skill);
    }

    public List<GAction> getSkills() {
        return skills;
    }

    public void endHour() {

    }

    public void push(Direction direction) {

    }

    public GObjectVisualizer getVisualizer() {
        return visualizer;
    }

    public void startHour() {

    }

    public boolean blocksMoveFor(GUnit unit) {
        return false;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public void removeMod(GMod mod) {
        mods.remove(mod);
    }

    public String getDescription() {
        return NameHelper.getName("unitDescription", getClass().getSimpleName());
    }

    public boolean isEnemyFor(GObject object) {
        return getPlayer().isEnemyFor(object);
    }
}
