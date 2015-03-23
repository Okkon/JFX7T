package sample;

public class Crossbow extends AbstractGAction{
    private final int minDamage;
    private final int maxDamage;
    private final int distance;

    public Crossbow(int minDamage, int maxDamage, int distance) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.distance = distance;
    }
}
