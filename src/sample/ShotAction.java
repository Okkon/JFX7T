package sample;

/**
 * Created by kondrashov on 24.03.2015.
 */
public class ShotAction extends AbstractGAction {
    protected int minDamage;
    protected int maxDamage;
    protected int distance;

    public ShotAction(GObject obj, int distance, int minDamage, int maxDamage) {
        setOwner(obj);
        this.distance = distance;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getDistance() {
        return distance;
    }


}
