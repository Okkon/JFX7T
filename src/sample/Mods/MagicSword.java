package sample.Mods;

import sample.Core.AbstractGMod;
import sample.Core.DamageType;
import sample.Core.GameModel;
import sample.Core.Hit;

public class MagicSword extends AbstractGMod {
    @Override
    public void onHit(Hit hit) {
        hit.setDamageType(DamageType.MAGIC);
        GameModel.MODEL.log("mods", "MagicSword");
    }
}
