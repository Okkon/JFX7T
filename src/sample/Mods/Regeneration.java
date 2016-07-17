package sample.Mods;


import sample.AbstractGMod;
import sample.GObject;
import sample.GUnit;
import sample.GameModel;

public class Regeneration extends AbstractGMod {
    public Regeneration(int power) {
        value = power;
    }

    @Override
    public void onTurnEnd(GObject object) {
        if (object instanceof GUnit) {
            GUnit gUnit = (GUnit) object;
            if (gUnit.isWounded()) {
                GameModel.MODEL.log("mods", "Regenaration", object);
                ((GUnit) object).recover(value);
            }
        }
    }
}
