package sample;

public interface GMod {
    void onHit(Hit hit);

    void onTakeHit(Hit hit);

    boolean canHideUnit(GObject observer, GObject aim);

    boolean blocksTower();

    void onTakeShot(Shell shell);

    void onTurnEnd(GObject object);

    void applyEffect(GObject gObject);

    boolean disablesAttack();

    String getName();

    String getDescription();
}
