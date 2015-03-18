package sample;

import java.util.Map;

public abstract class GObject implements Selectable {
    protected GameCell place;
    protected Player player;
    protected AbstractGAction baseAction = new ShiftAction(this);

    public GObject() {
    }

    public void takeHit(Hit hit) {

    }

    @Override
    public void select(GAction action) {
        GameModel.MODEL.setAction(baseAction);
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
}
