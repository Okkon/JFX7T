package sample.GlobalMods;

import sample.Core.GObject;
import sample.Core.GUnit;
import sample.Core.GameModel;
import sample.Core.GlobalMod;
import sample.Events.AttackEvent;
import sample.Events.ShiftEvent;
import sample.GActions.AttackAction;


public class GuardMod<T extends ShiftEvent, H extends GUnit> extends GlobalMod<T, H> {
    private static final GuardMod INSTANCE = new GuardMod();

    private GuardMod() {
        this.eventClass = ShiftEvent.class;
    }

    public static GuardMod getInstance() {
        return INSTANCE;
    }

    @Override
    public void doBeforeEvent(T event) {
        for (H unit : getHolders()) {
            GObject object = event.getObject();
            if (GameModel.MODEL.isNear(unit, object) && unit.isEnemyFor(object)) {
                AttackAction attackAction = unit.getAttackAction();
                attackAction.setOwner(unit);

                final AttackEvent attackEvent =
                        new AttackEvent()
                                .setAttackAction(attackAction)
                                .setAttacker(unit)
                                .setAim(object);
                attackEvent.process();
            }
        }
    }
}
