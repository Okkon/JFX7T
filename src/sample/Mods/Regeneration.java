package sample.Mods;


import sample.Core.AbstractGMod;
import sample.Core.GObject;
import sample.Core.GUnit;
import sample.Core.GameModel;

public class Regeneration extends AbstractGMod {
    public Regeneration(int power) {
        value = power;
    }

    @Override
    public void onTurnEnd(GObject object) {
        if (object instanceof GUnit) {
            GUnit gUnit = (GUnit) object;
            if (gUnit.isWounded()) {
                GameModel.MODEL.log("mods", "Regeneration", object);
                ((GUnit) object).recover(value);
            }
        }
    }
}
