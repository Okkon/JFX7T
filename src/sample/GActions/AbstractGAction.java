package sample.GActions;

import sample.Core.*;
import sample.Helpers.NameHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static sample.Filters.FilterFactory.FilterType;
import static sample.Filters.FilterFactory.getFilter;


public abstract class AbstractGAction implements GAction {
    protected List<GFilter> ownerFilters = new ArrayList<>();
    protected List<PlaceHaving> aims = new ArrayList<>();
    protected List<GFilter> aimFilters = new ArrayList<>();
    protected List<GFilter> preferableAimFilters = new ArrayList<>();
    protected GObject actor;
    protected GameModel model = GameModel.MODEL;
    protected AimType aimType = AimType.Anything;

    protected void addAimFilter(FilterType filter, String error, Object... params) {
        aimFilters.add(getFilter(filter, error, params));
    }

    protected void addAimFilter(GFilter filter) {
        aimFilters.add(filter);
    }

    protected void addPreferableAimFilter(GFilter filter) {
        preferableAimFilters.add(filter);
    }

    protected void removeAimFilter(Class filterClass) {
        aimFilters.remove(findAimFilterByClass(filterClass));
    }

    protected GFilter findAimFilterByClass(Class filterClass) {
        for (GFilter filter : aimFilters) {
            if (filterClass.isInstance(filter)) {
                return filter;
            }
        }
        return null;
    }

    @Override
    public void cancel() {
        if (aims.size() > 0) {
            aims.remove(aims.size() - 1);
            onSelect();
        } else {
            model.select(null);
            model.setAction(model.getPhaseAction());
        }
    }

    public List<GFilter> getAimFilters() {
        return aimFilters;
    }

    @Override
    public void onSelect() {
        if (!allAimsSelected()) {
            setAimFilters();
            model.showSelectionPossibility(getPossibleAims());
        } else {
            perform();
        }
    }

    protected boolean allAimsSelected() {
        //action performs if it has selected aims, or if it can't have any aims.
        return aimFilters.size() == 0 || aims.size() > 0;
    }

    protected void setAimFilters() {
        //place to add filters
    }

    @Override
    public void perform() {
        logActionStart();
        model.showSelectionPossibility(null);
        doAction();
        aims.clear();
        afterPerform();
        model.getPhase().next(this);
    }

    @Override
    public String getDescription() {
        return NameHelper.getName("skillDescription", getClass().getSimpleName());
    }

    @Override
    public List<? extends PlaceHaving> getPossibleAims() {
        List<? extends PlaceHaving> possibleAims = Collections.EMPTY_LIST;
        List<GFilter> aimFilters = new ArrayList<>(this.aimFilters);
        aimFilters.addAll(preferableAimFilters);
        if (actor != null) {
            for (GFilter aimFilter : aimFilters) {
                aimFilter.setObj(actor);
            }
        }
        if (AimType.Cell.equals(aimType)) {
            possibleAims = model.getCells(aimFilters);
        }
        if (AimType.Object.equals(aimType)) {
            possibleAims = model.getObjects(aimFilters);
        }
        if (AimType.ObjectAndCells.equals(aimType)) {
            possibleAims = model.getAll(aimFilters);
        }
        return possibleAims;
    }

    @Override
    public int estimate(PlaceHaving aim) {
        return 0;
    }

    @Override
    public void tryToSelectAction(PlaceHaving obj) {
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
            model.log("base", "ActionPerformed", actor != null ? actor : model.getActivePlayer().getName(), getName());
        }
    }

    protected boolean needsLogging() {
        return true;
    }

    protected void afterPerform() {
//        GraphicsHelper.getInstance().play();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public GObject getActor() {
        return actor;
    }

    public void setActor(GObject actor) {
        this.actor = actor;
    }

    public abstract void doAction();

    @Override
    public boolean canSelect(PlaceHaving obj) {
        for (GFilter filter : aimFilters) {
            filter.setObj(getActor());
            if (!filter.check(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canBeSelected() {
        for (GFilter filter : ownerFilters) {
            if (!filter.check(getActor())) {
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
