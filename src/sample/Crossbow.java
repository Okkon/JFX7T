package sample;

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
        shell.setName("Arrow");
        shell.setVisualizer(new ArrowShell());
    }
}
