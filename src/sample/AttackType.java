package sample;

/**
 * Created by kondrashov on 19.03.2015.
 */
public interface AttackType {
    AttackType DEFAULT = new BaseAttackType();

    void attack(GUnit unit, GObject obj);
}
