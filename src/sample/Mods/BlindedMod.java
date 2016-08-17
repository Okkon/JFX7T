package sample.Mods;

import sample.Core.AbstractGMod;

public class BlindedMod extends AbstractGMod {
    @Override
    public boolean disablesAttack() {
        return true;
    }
}
