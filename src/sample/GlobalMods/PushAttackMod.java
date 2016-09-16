package sample.GlobalMods;

import sample.Core.GMod2;
import sample.Core.GObject;
import sample.Core.Hit;
import sample.Events.HitEvent;

public class PushAttackMod<T extends HitEvent, H extends GObject> extends GMod2<T, H> {
    private static final PushAttackMod INSTANCE = new PushAttackMod();

    private PushAttackMod() {
        eventClass = HitEvent.class;
    }

    public static PushAttackMod getInstance() {
        return INSTANCE;
    }

    @Override
    public void doAfterEvent(T event) {
        Hit hit = event.getHit();
        if (!getHolders().contains(hit.getAttacker())) {
            return;
        }
        if (hit.getTakenDamage() > 0) {
            hit.getAim().push(hit.getDirection());
        }
    }
}
