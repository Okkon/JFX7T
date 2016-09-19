package sample.Core;

import sample.Direction;
import sample.Events.ShiftEvent;
import sample.GActions.SelectAction;
import sample.Helpers.NameHelper;
import sample.XY;

import java.util.ArrayList;
import java.util.List;

public abstract class GObject implements Selectable, PlaceHaving {
    protected GObjectVisualizer visualizer;
    protected GameCell place;
    protected Player player;
    protected GAction baseAction = SelectAction.getInstance();
    protected List<GMod> mods = new ArrayList<>();
    protected List<GlobalMod> globalMods = new ArrayList<>();
    protected List<GAction> skills = new ArrayList<>();
    protected List<GAura> auras = new ArrayList<>();


    public GObject() {
    }

    @Override
    public String toString() {
        return "object" + (place != null ? place.getXy().toString() : "");
    }

    public int takeHit(Hit hit) {
        for (GMod mod : getMods()) {
            mod.onTakeHit(hit);
        }
        return 0;
    }

    public void die(Hit hit) {
        GameModel.MODEL.getObjects().remove(this);
        place.setObj(null);
        visualizer.die(place);
        ArrayList<GlobalMod> modsCopyList = new ArrayList<>(getGlobalMods());
        for (GlobalMod mod : modsCopyList) {
            mod.unregister(this);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void shift(GameCell cellToGo) {
        if (cellToGo != null && cellToGo.getObj() == null) {
            final ShiftEvent shiftEvent = new ShiftEvent(this, cellToGo);
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
        List<GMod> modList = new ArrayList<>();
        modList.addAll(mods);
        if (place != null) {
            GameModel.MODEL.getAuras().stream().filter(aura -> aura.validFor(this)).forEach(aura -> {
                modList.add(aura.getMod());
            });
        }
        return modList;
    }

    public void addMod(GMod mod) {
        mods.add(mod);
        final GObjectVisualizer objectVisualizer = getVisualizer();
        if (objectVisualizer != null) {
            objectVisualizer.applyEffect(mod);
        }
    }

    public void addGlobalMod(GlobalMod mod) {
        globalMods.add(mod);
    }

    public void addSkill(GAction skill) {
        this.skills.add(0, skill);
    }

    public List<GAction> getSkills() {
        return skills;
    }

    public void onEndHour() {

    }

    public void push(Direction direction) {

    }

    public GObjectVisualizer getVisualizer() {
        return visualizer;
    }

    public void onStartHour() {

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

    public List<GlobalMod> getGlobalMods() {
        return globalMods;
    }
}
