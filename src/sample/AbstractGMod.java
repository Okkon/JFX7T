package sample;


public class AbstractGMod implements GMod {
    protected int value;

    @Override
    public boolean canHideUnit(GObject observer, GObject aim) {
        return false;
    }

    @Override
    public boolean blocksTower() {
        return false;
    }

    @Override
    public void onTakeShot(Shell shell) {

    }

    @Override
    public void onTurnEnd(GObject object) {

    }

    @Override
    public void applyEffect(GObject gObject) {

    }

    @Override
    public boolean disablesAttack() {
        return false;
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
