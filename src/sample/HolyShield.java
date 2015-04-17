package sample;


public class HolyShield extends AbstractGMod {
    @Override
    public void onTakeHit(Hit hit) {
        hit.setDamage(0);
        hit.getAim().removeMod(this);
    }
}
