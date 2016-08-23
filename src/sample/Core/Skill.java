package sample.Core;

import sample.Filters.CanActFilter;
import sample.GActions.AbstractGAction;

public abstract class Skill extends AbstractGAction {

    public Skill() {
        ownerFilters.add(new CanActFilter().setError("UnitCantAct"));
    }

    @Override
    public void cancel() {
        if (aims.size() > 0) {
            aims.remove(aims.size() - 1);
            onSelect();
        } else {
            model.select(getOwner());
        }
    }

    @Override
    public GUnit getOwner() {
        return (GUnit) super.getOwner();//TODO: what about tower?
    }

    public boolean endsTurn() {
        return true;
    }
}
