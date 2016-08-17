package sample.Mods;


import sample.Core.AbstractGMod;
import sample.Core.GameModel;
import sample.Core.Hit;

public class HolyShield extends AbstractGMod {
    @Override
    public void onTakeHit(Hit hit) {
        hit.absorb();
        hit.getAim().removeMod(this);
        GameModel.MODEL.log("mods", "HolyShield");
    }
}
