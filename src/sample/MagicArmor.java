package sample;

public class MagicArmor extends AbstractGMod {

    @Override
    public void onTakeHit(Hit hit) {
        final int reduceDamage = Hit.reduceDamage(hit, value, DamageType.MAGIC);
        if (reduceDamage > 0)
            GameModel.MODEL.log("mods", "MagicArmor", reduceDamage);
    }

    public MagicArmor(int value) {
        this.value = value;
    }
}
