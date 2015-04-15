package sample;


public class Regeneration extends AbstractGMod {
    public Regeneration(int power) {
        value = power;
    }

    @Override
    public void onTurnEnd(GObject object) {
        if (object instanceof GUnit) {
            GUnit gUnit = (GUnit) object;
            if (gUnit.isWounded()) {
                final int recoveredHp = ((GUnit) object).recover(value);
                if (recoveredHp > 0) {
                    GameModel.MODEL.log("mods", "Regenaration", object);
                }
            }
        }
    }
}
