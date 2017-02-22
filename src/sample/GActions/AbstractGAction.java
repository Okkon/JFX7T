package sample.GActions;

import sample.Core.*;
import sample.Events.FilterSelectionEvent;
import sample.Helpers.NameHelper;

import java.util.ArrayList;
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
        ArrayList<GFilter> filters = new ArrayList<>(aimFilters);
        new FilterSelectionEvent(this, filters).process();
        return filters;
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
        List<GFilter> aimFilters = getAimFilters();
        aimFilters.addAll(preferableAimFilters);
        if (actor != null) {
            aimFilters.forEach(gFilter -> gFilter.setObj(actor));
        }
        switch (aimType) {
            case Cell:
                return model.getCells(aimFilters);
            case Object:
                return model.getObjects(aimFilters);
            default:
                return model.getAll(aimFilters);
        }

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
        for (GFilter filter : getAimFilters()) {
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
