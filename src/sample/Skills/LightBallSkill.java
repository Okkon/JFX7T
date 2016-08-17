package sample.Skills;

import sample.Core.DamageType;
import sample.Core.GObject;
import sample.Core.Hit;
import sample.Core.Shell;
import sample.Mods.BlindedMod;
import sample.Shells.LightballShell;

public class LightBallSkill extends ShotAction {
    public LightBallSkill(int minDam, int randDam, int maxDistance) {
        super(maxDistance, minDam, randDam);
    }

    @Override
    protected Shell createShell() {
        return new Shell() {
            @Override
            public void hit(GObject obj) {
                final Hit hit = Hit.createHit(attacker, obj, minDamage, maxDamage, damageType);
                final int takenDamage = obj.takeHit(hit);
                if (takenDamage > 0) {
                    obj.addMod(new BlindedMod());
                }
            }
        };
    }

    @Override
    protected void configureShell(Shell shell) {
        shell.setName("LightBallSkill");
        shell.setDamageType(DamageType.MAGIC);
        shell.setVisualizer(new LightballShell());
    }
}
