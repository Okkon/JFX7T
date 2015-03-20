package sample;

/**
 * Created by kondrashov on 20.03.2015.
 */
public class Armor extends AbstractGMod {
    private final int value;

    @Override
    public void onTakeHit(Hit hit) {
        final int reduceDamage = Hit.reduceDamage(hit, value, DamageType.MAGIC);
        GameModel.MODEL.log("Magic Armor absorbed " + reduceDamage + " damage!");
    }

    public Armor(int value) {
        this.value = value;
    }
}
