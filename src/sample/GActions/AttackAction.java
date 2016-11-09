package sample.GActions;

import sample.Core.*;
import sample.Events.AttackEvent;
import sample.Events.HitEvent;
import sample.Filters.FilterFactory;
import sample.Filters.IsNearFilter;

import java.util.ArrayList;
import java.util.List;

import static sample.Filters.FilterFactory.FilterType.CAN_BE_ATTACKED;

public class AttackAction extends Skill {
    private static final AttackAction INSTANCE = new AttackAction();

    protected AttackAction() {
        super();
        aimType = AimType.Object;
        addAimFilter(IsNearFilter.getInstance().setError("AimIsTooFar"));
        addAimFilter(new FilterFactory.ClassFilter().setClass(GUnit.class).setError("NotUnit"));
        addAimFilter(CAN_BE_ATTACKED, "CanAttack");
    }

    public static AttackAction getInstance() {
        return INSTANCE;
    }

    @Override
    public void doAction() {
        final AttackEvent attackEvent =
                new AttackEvent()
                        .setAttackAction(this)
                        .setAttacker((GUnit) getOwner())
                        .setAim(getAim());
        attackEvent.process();
    }

    public void attack(GUnit attacker, PlaceHaving aim) {
        for (GObject aimToHit : getAimsToHit(aim)) {
            Hit hit = Hit.createHit(attacker, aimToHit);
            final List<GMod> mods = attacker.getMods();
            for (GMod mod : mods) {
                mod.onHit(hit);
            }
            attacker.getVisualizer().startAttack(hit);
            new HitEvent(hit).process();
        }
    }

    protected List<? extends GObject> getAimsToHit(PlaceHaving aim) {
        ArrayList<GObject> result = new ArrayList<>();
        if (aim instanceof GObject) {
            GObject gObject = (GObject) aim;
            result.add(gObject);
        }
        return result;
    }
}
