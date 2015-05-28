package sample.Mods;

import sample.AbstractGMod;

public class BlindedMod extends AbstractGMod {
    @Override
    public boolean disablesAttack() {
        return true;
    }
}
