package sample;

public class MagicSword extends AbstractGMod {
    @Override
    public void onHit(Hit hit) {
        hit.setDamageType(DamageType.MAGIC);
        GameModel.MODEL.log("Magic sword activated!");
    }
}
