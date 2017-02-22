package sample.Events;

import sample.Core.GEvent;
import sample.Core.GFilter;
import sample.GActions.AbstractGAction;

import java.util.List;


public class FilterSelectionEvent extends GEvent {
    final private AbstractGAction action;
    private final List<GFilter> aimFilters;

    public FilterSelectionEvent(AbstractGAction abstractGAction, List<GFilter> aimFilters) {
        action = abstractGAction;
        this.aimFilters = aimFilters;
    }

    @Override
    protected void perform() {
        //just waiting for listeners work
    }

    public AbstractGAction getAction() {
        return action;
    }

    public List<GFilter> getAimFilters() {
        return aimFilters;
    }
}
