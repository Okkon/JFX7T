package sample.Core;

import sample.Events.ShiftEvent;
import sample.Tower.Tower;

public interface GMod {
    void onHit(Hit hit);

    void onTakeHit(Hit hit);

    boolean canHideUnit(GObject observer, GObject aim);

    boolean blocksTower(Tower tower);

    void onTakeShot(Shell shell);

    void onTurnEnd(GObject object);

    boolean disablesAttack();

    String getName();

    String getDescription();

    void onBeforeShift(ShiftEvent shiftEvent);
}