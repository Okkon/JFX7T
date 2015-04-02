package sample;

import java.util.Random;

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
        return createHit(attacker, aim, attacker.getMinDamage(), attacker.getRandDamage(), DamageType.PHYSICAL);
    }

    public static Hit createHit(GObject attacker, GObject aim, int damage) {
        return createHit(attacker, aim, damage, 0, DamageType.PHYSICAL);
    }

    public static Hit createHit(GObject attacker, GObject aim, int minDamage, int randDamage) {
        return createHit(attacker, aim, minDamage, randDamage, DamageType.PHYSICAL);
    }

    public static Hit createHit(GObject attacker, GObject aim, int minDamage, int randDamage, DamageType damageType) {
        final Hit hit = new Hit();
        hit.attacker = attacker;
        hit.aim = aim;
        hit.damageType = damageType;
        hit.damage = generateDamage(minDamage, randDamage);
        hit.from = attacker.getPlace();
        hit.to = aim.getPlace();

        return hit;

    }

    public static int generateDamage(int minDamage, int randDamage) {
        Random r = new Random();
        int d = minDamage;
        for (int i = 0; i < randDamage; i++) {
            if (r.nextBoolean()) {
                d++;
            }
        }
        return d;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public static int reduceDamage(Hit hit, int val, DamageType damageType) {
        if (hit.damageType != damageType) {
            return 0;
        }
        final int hitDamage = hit.getDamage();
        int reducedDamage = Math.min(hitDamage, val);
        hit.setDamage(hitDamage - reducedDamage);
        return reducedDamage;
    }


    public void setDamage(int damage) {
        this.damage = damage;
    }
}
