package sample.Mods;

import sample.Core.AbstractGMod;

public class BlindedMod extends AbstractGMod {
    private static final BlindedMod INSTANCE = new BlindedMod();

    private BlindedMod() {
    }

    public static BlindedMod getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean disablesAttack() {
        return true;
    }
}
