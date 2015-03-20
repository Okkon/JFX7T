package sample;

import java.util.ArrayList;
import java.util.List;

public abstract class GObject implements Selectable {
    protected GObjectVisualizer visualizer;
    protected GameCell place;
    protected Player player;
    protected AbstractGAction baseAction = new SelectAction();
    private List<GMod> mods = new ArrayList<GMod>();

    public GObject() {
    }

    public void takeHit(Hit hit) {
        die();
    }

    protected void die() {
        GameModel.MODEL.getObjects().remove(this);
        visualizer.die(place);
    }

    @Override
    public void select(GAction action) {
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
}
