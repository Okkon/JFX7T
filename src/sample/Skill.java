package sample;

import sample.Filters.CanActFilter;
import sample.GActions.AbstractGAction;

public abstract class Skill extends AbstractGAction {

    public Skill() {
        ownerFilters.add(new CanActFilter().setError("UnitCantAct"));
    }

    public boolean endsTurn() {
        return true;
    }
}
