package sample.GActions;

import sample.*;
import sample.Shells.AstralArrowShell;

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

