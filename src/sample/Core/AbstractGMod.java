package sample.Core;


import sample.Events.ShiftEvent;
import sample.Helpers.NameHelper;
import sample.Tower.Tower;

public abstract class AbstractGMod implements GMod {
    protected int value;

    @Override
    public boolean canHideUnit(GObject observer, GObject aim) {
        return false;
    }

    @Override
    public boolean blocksTower(Tower tower) {
        return false;
    }

    @Override
    public void onTakeShot(Shell shell) {

    }

    @Override
    public void onTurnEnd(GObject object) {

    }

    @Override
    public boolean disablesAttack() {
        return false;
    }

    @Override
    public String getName() {
        return NameHelper.getName("modNames", getClass().getSimpleName());
    }

    @Override
    public String getDescription() {
        return NameHelper.getName("modDescription", getClass().getSimpleName(), value);
    }

    @Override
    public void onHit(Hit hit) {

    }

    @Override
    public void onTakeHit(Hit hit) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
