package sample.GActions;

import sample.Core.*;
import sample.Events.AttackEvent;
import sample.Events.HitEvent;
import sample.Filters.FilterFactory;
import sample.Filters.IsNearFilter;

import java.util.ArrayList;
import java.util.List;

public class AttackAction extends Skill {
    private static final AttackAction INSTANCE = new AttackAction();

    protected AttackAction() {
        super();
        aimType = AimType.Object;
        addAimFilter(new IsNearFilter().setError("AimIsTooFar"));
        addAimFilter(new FilterFactory.ClassFilter().setClass(GUnit.class).setError("NotUnit"));
    }

    public static AttackAction getInstance() {
        return INSTANCE;
    }

    @Override
    public void doAction() {
        final AttackEvent attackEvent =
                new AttackEvent()
                        .setAttackAction(this)
                      .setAttacker((GUnit) getActor())
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
