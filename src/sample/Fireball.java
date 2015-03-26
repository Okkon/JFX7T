package sample;

/**
 * Created by kondrashov on 26.03.2015.
 */
public class Fireball extends ShotAction {
    public Fireball(int minDam, int randDam, int maxDistance) {
        super(minDam, randDam, maxDistance);
    }

    @Override
    protected void configureShell(Shell shell) {
        shell.setName("Fireball");
        shell.setDamageType(DamageType.MAGIC);
    }
}
