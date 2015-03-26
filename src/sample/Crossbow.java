package sample;

public class Crossbow extends ShotAction {

    public Crossbow(int minDamage, int maxDamage, int distance) {
        super(distance, minDamage, maxDamage);
    }

    @Override
    protected Shell createShell() {
        return new Shell() {
            @Override
            public void stopCheck(GObject obj) {

            }
        };
    }

    @Override
    protected void configureShell(Shell shell) {
        shell.setName("Arrow");
    }
}
