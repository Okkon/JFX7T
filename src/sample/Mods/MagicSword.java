package sample.Mods;

import sample.AbstractGMod;
import sample.DamageType;
import sample.GameModel;
import sample.Hit;

public class MagicSword extends AbstractGMod {
    @Override
    public void onHit(Hit hit) {
        hit.setDamageType(DamageType.MAGIC);
        GameModel.MODEL.log("mods", "MagicSword");
    }
}
