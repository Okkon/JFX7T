package sample;

import java.util.Random;

/**
 * Created by kondrashov on 25.02.2015.
 */
public class Hit {
    private int damage;
    private DamageType damageType;
    private GObject attacker;
    private GObject aim;
    private GameCell from;
    private GameCell to;

    public int getDamage() {
        return damage;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public GObject getAttacker() {
        return attacker;
    }

    public GObject getAim() {
        return aim;
    }

    public GameCell getFrom() {
        return from;
    }

    public GameCell getTo() {
        return to;
    }

    public static Hit createHit(GUnit attacker, GObject aim) {
        final Hit hit = new Hit();
        hit.attacker = attacker;
        hit.aim = aim;
        hit.damageType = DamageType.PHYSICAL;
        hit.damage = generateDamage(attacker.getDamage(), attacker.getRandDamage());
        hit.from = attacker.getPlace();
        hit.to = aim.getPlace();

        return hit;
    }

    private static int generateDamage(int damage, int randDamage) {
        Random r = new Random();
        int d = damage;
        for (int i = 0; i < randDamage; i++) {
            if (r.nextBoolean()) {
                d++;
            }
        }
        return d;
    }
}
