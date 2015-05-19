package sample.Mods;


import sample.AbstractGMod;
import sample.GObject;

public class Masking extends AbstractGMod {
    @Override
    public void applyEffect(GObject gObject) {
        gObject.getVisualizer().applyEffect("hidden");
    }

    @Override
    public boolean canHideUnit(GObject observer, GObject aim) {
        return observer.isEnemyFor(aim);
    }
}
