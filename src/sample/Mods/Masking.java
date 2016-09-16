package sample.Mods;


import sample.Core.AbstractGMod;
import sample.Core.GObject;

public class Masking extends AbstractGMod {
    private static final Masking INSTANCE = new Masking();

    private Masking() {
    }

    public static Masking getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean canHideUnit(GObject observer, GObject aim) {
        return observer.isEnemyFor(aim);
    }
}
