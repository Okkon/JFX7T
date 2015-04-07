package sample;


public interface AttackStyle {
    AttackStyle DEFAULT = new BaseAttackStyle();

    void attack(GUnit unit, GObject obj);
}
