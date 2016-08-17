package sample.Mods;


import sample.Core.AbstractGMod;
import sample.Core.GObject;

public class Masking extends AbstractGMod {
    @Override
    public boolean canHideUnit(GObject observer, GObject aim) {
        return observer.isEnemyFor(aim);
    }
}
