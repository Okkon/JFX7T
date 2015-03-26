package sample;

import java.util.ArrayList;
import java.util.List;

public abstract class GObject implements Selectable {
    protected GObjectVisualizer visualizer;
    protected GameCell place;
    protected Player player;
    protected AbstractGAction baseAction = new SelectAction();
    private List<GMod> mods = new ArrayList<GMod>();
    protected List<GAction> skills = new ArrayList<GAction>();


    public GObject() {
    }

    public void takeHit(Hit hit) {
        for (GMod mod : getMods()) {
            mod.onTakeHit(hit);
        }
    }

    protected void die() {
        GameModel.MODEL.getObjects().remove(this);
        place.setObj(null);
        visualizer.die(place);
    }

    @Override
    public void select() {
        GameModel.MODEL.setAction(baseAction);
    }

    @Override
    public void showSelectionPossibility() {

    }

    @Override
    public void hideSelectionPossibility() {

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void shift(GameCell cellToGo) {
        if (cellToGo != null && cellToGo.getObj() == null) {
            final GameCell currentCell = getPlace();
            currentCell.setObj(null);
            this.place = cellToGo;
            cellToGo.setObj(this);
            visualizer.changePlace(currentCell, cellToGo);
        }
    }

    public AbstractGAction getBaseAction() {
        return baseAction;
    }

    protected boolean isFriendly(GObject gObject) {
        return gObject.getPlayer().equals(getPlayer());
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

    }

    public List<GMod> getMods() {
        return mods;
    }

    public void addMod(GMod mod) {
        mods.add(mod);
    }

    public void addSkill(GAction skill) {
        this.skills.add(0, skill);
    }

    public List<GAction> getSkills() {
        return skills;
    }

    public void endHour() {

    }
}
