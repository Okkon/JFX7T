package sample.Mods;

import sample.Core.AbstractGMod;
import sample.Core.DamageType;
import sample.Core.GameModel;
import sample.Core.Shell;

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
