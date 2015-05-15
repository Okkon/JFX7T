package sample.GActions;

import sample.GObject;
import sample.Shell;
import sample.Shells.ArrowShell;
import sample.ShotAction;
import sample.Tower;

public class Crossbow extends ShotAction {

    public Crossbow(int minDamage, int randDamage, int distance) {
        super(distance, minDamage, randDamage);
    }

    @Override
    protected Shell createShell() {
        return new Shell() {
            @Override
            public void stopCheck(GObject obj) {
                if (obj instanceof Tower || minDamage < 0) {
                    this.stopped = true;
                }
            }
        };
    }

    @Override
    protected void configureShell(Shell shell) {
        shell.setName("Crossbow");
        shell.setVisualizer(new ArrowShell());
    }
}
