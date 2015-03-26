package sample;

public class MagicArmor extends AbstractGMod {
    private final int value;

    @Override
    public void onTakeHit(Hit hit) {
        final int reduceDamage = Hit.reduceDamage(hit, value, DamageType.MAGIC);
        if (reduceDamage > 0)
            GameModel.MODEL.log("Magic Armor absorbed " + reduceDamage + " damage!");
    }

    public MagicArmor(int value) {
        this.value = value;
    }
}
