package sample.GActions;

import sample.*;
import sample.Filters.GFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static sample.Filters.FilterFactory.FilterType;
import static sample.Filters.FilterFactory.getFilter;


public abstract class AbstractGAction implements GAction {
    protected List<GFilter> ownerFilters = new ArrayList<GFilter>();
    protected List<PlaceHaving> aims = new ArrayList<PlaceHaving>();
    protected List<List<GFilter>> filters = new ArrayList<List<GFilter>>();
    protected List<GFilter> aimFilters = new ArrayList<GFilter>();
    protected GObject owner;
    protected boolean endsTurn = false;
    protected AimType aimType = AimType.Anything;

    protected void addAimFilter(FilterType filter, String error, Object... params) {
        aimFilters.add(getFilter(filter, error, params));
    }

    @Override
    public void onSelect() {
        GameModel.MODEL.showSelectionPossibility(getAims());
    }

    @Override
    public void perform(Selectable obj) {
        if (canSelect(obj)) {
            logActionStart();
            GameModel.MODEL.showSelectionPossibility(null);
            act(obj);
            afterPerform();
        }
    }

    @Override
    public String getDescription() {
        return NameHelper.getName("skillDescription", getClass().getSimpleName());
    }

    @Override
    public List<? extends PlaceHaving> getAims() {
        List<? extends PlaceHaving> possibleAims = Collections.EMPTY_LIST;
        for (GFilter aimFilter : aimFilters) {
            aimFilter.setObj(owner);
        }
        if (AimType.Cell.equals(aimType)) {
            possibleAims = GameModel.MODEL.getCells(aimFilters);
        }
        if (AimType.Object.equals(aimType)) {
            possibleAims = GameModel.MODEL.getObjects(aimFilters);
        }
        return possibleAims;
    }

    @Override
    public int estimate(PlaceHaving aim) {
        return 0;
    }

    @Override
    public void tryToSelect(PlaceHaving obj) {
        if (canSelect(obj)) {
            aims.add(obj);
            if (aims.size() == filters.size()) {
                //perform();
            }
        }
    }

    private void logActionStart() {
        if (needsLogging()) {
            GameModel.MODEL.log("base", "ActionPerformed", owner != null ? owner : GameModel.MODEL.getActivePlayer().getName(), getName());
        }
    }

    protected boolean needsLogging() {
        return true;
    }

    protected void afterPerform() {
        if (endsTurn || (getOwner() != null && !getOwner().canAct())) {
            GameModel.MODEL.endTurn();
        } else if (getOwner() != null) {
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
        return NameHelper.getName("skillNames", getClass().getSimpleName());
    }
}
