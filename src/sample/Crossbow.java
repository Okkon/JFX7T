package sample;

public class Crossbow extends ShotAction {

    public Crossbow(int minDamage, int maxDamage, int distance) {
        super(distance, minDamage, maxDamage);
    }

    @Override
    protected void configureShell(Shell shell) {
        shell.setName("Arrow");
    }
}
