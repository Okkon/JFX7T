package sample;

import sample.Events.AttackEvent;
import sample.Filters.IsNearFilter;

import static sample.Filters.FilterFactory.FilterType.CAN_BE_ATTACKED;
import static sample.Filters.FilterFactory.FilterType.IS_UNIT;

public class AttackAction extends Skill {
    public static final AttackAction DEFAULT = new AttackAction();

    private AttackAction() {
        super();
        aimType = AimType.Object;
        getAimFilters().add(new IsNearFilter().setError("AimIsTooFar"));
        addAimFilter(IS_UNIT, "NotUnit");
        addAimFilter(CAN_BE_ATTACKED, "CanAttack");
    }

    @Override
    public void doAction() {
        GUnit attacker = (GUnit) getOwner();
        GObject aim = ((GObject) getAim());
        final AttackEvent attackEvent = new AttackEvent().setAttacker(attacker).setAim(aim);
        attackEvent.process();
    }
}
