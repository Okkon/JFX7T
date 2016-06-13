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
    protected GObject owner;
    protected boolean endsTurn = false;
    protected AimType aimType = AimType.Anything;

    protected void addAimFilter(FilterType filter, String error, Object... params) {
        getAimFilters().add(getFilter(filter, error, params));
    }

    @Override
    public void cancel() {
        if (aims.size() > 0) {
            aims.remove(aims.size() - 1);
            onSelect();
        } else {
            GameModel.MODEL.setAction(GameModel.CURRENT_PHASE_ACTION);
        }
    }

    public List<GFilter> getAimFilters() {
        if (filters.isEmpty()) {
            filters.add(new ArrayList<GFilter>());
        }
        return filters.get(aims.size());
    }

    @Override
    public void onSelect() {
        if (aims.size() < filters.size()) {
            GameModel.MODEL.showSelectionPossibility(getPossibleAims());
        } else {
            perform();
        }
    }

    @Override
    public void perform() {
        logActionStart();
        GameModel.MODEL.showSelectionPossibility(null);
        doAction();
        aims.clear();
        afterPerform();
        GameModel.MODEL.getPhase().next(this);
    }

    @Override
    public String getDescription() {
        return NameHelper.getName("skillDescription", getClass().getSimpleName());
    }

    @Override
    public List<? extends PlaceHaving> getPossibleAims() {
        List<? extends PlaceHaving> possibleAims = Collections.EMPTY_LIST;
        for (GFilter aimFilter : getAimFilters()) {
            aimFilter.setObj(owner);
        }
        if (AimType.Cell.equals(aimType)) {
            possibleAims = GameModel.MODEL.getCells(getAimFilters());
        }
        if (AimType.Object.equals(aimType)) {
            possibleAims = GameModel.MODEL.getObjects(getAimFilters());
        }
        if (AimType.ObjectAndCells.equals(aimType)) {
            possibleAims = GameModel.MODEL.getAll(getAimFilters());
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
            onSelect();
        }
    }

    @Override
    public List<PlaceHaving> getAims() {
        return aims;
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

    public abstract void doAction();

    @Override
    public boolean canSelect(Selectable obj) {
        for (GFilter filter : getAimFilters()) {
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

    public PlaceHaving getAim() {
        return aims.isEmpty() ? null : aims.get(0);
    }
}
