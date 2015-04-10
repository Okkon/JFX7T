package sample;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractGAction implements GAction {
    protected List<GFilter> aimFilters = new ArrayList<GFilter>();
    protected List<GFilter> ownerFilters = new ArrayList<GFilter>();
    protected GObject owner;
    protected boolean endsTurn = false;

    @Override
    public void onSelect() {

    }

    @Override
    public void perform(Selectable obj) {
        act(obj);
        if (endsTurn) {
            GameModel.MODEL.endTurn();
        }
        GameModel.MODEL.setLastActedUnit(getOwner());
        GraphicsHelper.getInstance().play();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public GObject getOwner() {
        return owner;
    }

    @Override
    public void setOwner(GObject owner) {
        this.owner = owner;
    }

    public void act(Selectable obj) {

    }

    @Override
    public boolean canSelect(Selectable obj) {
        for (GFilter filter : aimFilters) {
            if (filter.getObj() == null) {
                filter.setObj(getOwner());
            }
            if (!filter.check(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canBeSelected() {
        for (GFilter filter : ownerFilters) {
            if (!filter.check(getOwner())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
