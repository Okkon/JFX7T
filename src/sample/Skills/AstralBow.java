package sample.Skills;

import sample.Core.DamageType;
import sample.Core.GObject;
import sample.Core.Shell;
import sample.Shells.AstralArrowShell;
import sample.Tower.Tower;

public class AstralBow extends ShotAction {

    public AstralBow(int minDamage, int randDamage, int distance) {
        super(distance, minDamage, randDamage);
    }

    @Override
    protected Shell createShell() {
        return new Shell() {
            @Override
            public void stopCheck(GObject obj) {
                if (obj instanceof Tower) {
                    this.stopped = true;
                }
            }
        };
    }

    @Override
    protected void configureShell(Shell shell) {
        shell.setName("AstralBow");
        shell.setDamageType(DamageType.ASTRAL);
        shell.setVisualizer(new AstralArrowShell());
    }
}

