package sample;

import java.util.ResourceBundle;

/**
 * Created by kondrashov on 20.03.2015.
 */
public class Armor extends AbstractGMod {
    @Override
    public void onTakeHit(Hit hit) {
        final int reduceDamage = Hit.reduceDamage(hit, value, DamageType.PHYSICAL);
        if (reduceDamage > 0)
            GameModel.MODEL.log(String.format(ResourceBundle.getBundle("sample/mods").getString("ArmorAbsorbedDamage"), reduceDamage));
    }

    public Armor(int value) {
        this.value = value;
    }
}
