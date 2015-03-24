package sample;

/**
 * Created by kondrashov on 24.03.2015.
 */
public class ShotAction extends AbstractGAction {
    protected final int minDamage;
    protected final int maxDamage;
    protected final int distance;

    public ShotAction(int distance, int minDamage, int maxDamage) {
        this.distance = distance;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }
}
