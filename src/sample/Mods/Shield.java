package sample.Mods;

import sample.AbstractGMod;
import sample.DamageType;
import sample.GameModel;
import sample.Shell;

public class Shield extends AbstractGMod {
    public Shield(int value) {
        this.value = value;
    }

    @Override
    public void onTakeShot(Shell shell) {
        final int reduceDamage = shell.reduceDamage(value, DamageType.PHYSICAL);
        if (reduceDamage > 0) {
            GameModel.MODEL.log("mods", "Shield", reduceDamage);
        }
    }
}
