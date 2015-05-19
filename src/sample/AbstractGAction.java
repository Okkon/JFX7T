package sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static sample.FilterFactory.FilterType;
import static sample.FilterFactory.getFilter;


public abstract class AbstractGAction implements GAction {
    protected List<GFilter> aimFilters = new ArrayList<GFilter>();
    protected List<GFilter> ownerFilters = new ArrayList<GFilter>();
    protected GObject owner;
    protected boolean endsTurn = false;
    protected AimType aimType;

    public AbstractGAction() {
        initialize();
    }

    protected void initialize() {
        aimType = AimType.Anything;
    }

    protected void addAimFilter(FilterType filter, String error, Object... params) {
        aimFilters.add(getFilter(filter, error, params));
    }

    @Override
    public void onSelect() {
        Collection<? extends Selectable> possibleAims = null;
        if (AimType.Cell.equals(aimType)) {
            possibleAims = GameModel.MODEL.getCells(aimFilters);
        }
        if (AimType.Object.equals(aimType)) {
            possibleAims = GameModel.MODEL.getObjects(aimFilters);
        }
        GameModel.MODEL.showSelectionPossibility(possibleAims);
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
        return NameHelper.getName("skillDescription", getClass().getSimpleName());
    }

    private void logActionStart() {
        GameModel.MODEL.log("base", "ActionPerformed", owner != null ? owner : GameModel.MODEL.getActivePlayer().getName(), getName());
    }

    protected void afterPerform() {
        if (endsTurn) {
            GameModel.MODEL.endTurn();
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
        return NameHelper.getName("skillNames", getClass().getSimpleName());
    }
}
