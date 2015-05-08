package sample.Mods;


import sample.AbstractGMod;
import sample.Hit;

public class HolyShield extends AbstractGMod {
    @Override
    public void onTakeHit(Hit hit) {
        hit.absorb();
        hit.getAim().removeMod(this);
    }
}
