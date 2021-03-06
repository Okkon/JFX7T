package sample.Mods;

import sample.Core.AbstractGMod;
import sample.Core.DamageType;
import sample.Core.GameModel;
import sample.Core.Hit;

public class Armor extends AbstractGMod {
    @Override
    public void onTakeHit(Hit hit) {
        final int reduceDamage = Hit.reduceDamage(hit, value, DamageType.PHYSICAL);
        if (reduceDamage > 0)
            GameModel.MODEL.log("mods", "ArmorAbsorbedDamage", reduceDamage);
    }

    public Armor(int value) {
        this.value = value;
    }
}
