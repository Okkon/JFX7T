package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


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
        if (canSelect(obj)) {
            logActionStart();
            act(obj);
            afterPerform();
        }
    }

    @Override
    public String getDescription() {
        return ResourceBundle.getBundle(MyConst.RESOURCE_BUNDLE_LOCATION + "skillDescription").getString(getClass().getSimpleName());
    }

    private void logActionStart() {
        GameModel.MODEL.log("base", "ActionPerformed", owner != null ? owner : "someone", getName());
    }

    protected void afterPerform() {
        if (endsTurn) {
            GameModel.MODEL.endTurn();
        } else {
            GameModel.MODEL.select(getOwner());
        }
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

    public abstract void act(Selectable obj);

    @Override
    public boolean canSelect(Selectable obj) {
        for (GFilter filter : aimFilters) {
            filter.setObj(getOwner());
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
        return ResourceBundle.getBundle(MyConst.RESOURCE_BUNDLE_LOCATION + "skillNames").getString(getClass().getSimpleName());
    }
}
