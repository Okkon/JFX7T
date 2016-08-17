package sample.GActions;

import sample.Core.*;
import sample.Events.AttackEvent;
import sample.Events.HitEvent;
import sample.Filters.FilterFactory;
import sample.Filters.IsNearFilter;

import java.util.List;

import static sample.Filters.FilterFactory.FilterType.CAN_BE_ATTACKED;

public class AttackAction extends Skill {
    public static final AttackAction DEFAULT = new AttackAction();

    protected AttackAction() {
        super();
        aimType = AimType.Object;
        getAimFilters().add(new IsNearFilter().setError("AimIsTooFar"));
        getAimFilters().add(new FilterFactory.ClassFilter().setClass(GUnit.class).setError("NotUnit"));
        addAimFilter(CAN_BE_ATTACKED, "CanAttack");
    }

    @Override
    public void doAction() {
        GUnit attacker = (GUnit) getOwner();
        GObject aim = ((GObject) getAim());
        final AttackEvent attackEvent = new AttackEvent().setAttacker(attacker).setAim(aim);
        attackEvent.process();
    }

    public void attack(GUnit attacker, GObject aim) {
        Hit hit = Hit.createHit(attacker, aim);
        final List<GMod> mods = attacker.getMods();
        for (GMod mod : mods) {
            mod.onHit(hit);
        }
        attacker.getVisualizer().startAttack(hit);
        GameModel.MODEL.log("base", "Hits", attacker, aim);
        new HitEvent(hit).process();
    }
}
