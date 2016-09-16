package sample.Mods;

import sample.Core.AbstractGMod;
import sample.Core.DamageType;
import sample.Core.GameModel;
import sample.Core.Hit;

public class MagicSword extends AbstractGMod {
    private static final MagicSword INSTANCE = new MagicSword();

    private MagicSword() {
    }

    public static MagicSword getInstance() {
        return INSTANCE;
    }

    @Override
    public void onHit(Hit hit) {
        hit.setDamageType(DamageType.MAGIC);
        GameModel.MODEL.log("mods", "MagicSword");
    }
}
