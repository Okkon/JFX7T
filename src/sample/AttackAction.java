package sample;

import java.util.List;

import static sample.Filters.FilterFactory.FilterType.*;

public class AttackAction extends Skill {
    public static final AttackAction DEFAULT = new AttackAction();

    private AttackAction() {
        super();
        aimType = AimType.Object;
        addAimFilter(IS_NEAR, "AimIsTooFar");
        addAimFilter(IS_UNIT, "NotUnit");
        addAimFilter(CAN_BE_ATTACKED, "CanAttack");
    }

    @Override
    public void doAction() {
        GUnit attacker = (GUnit) getOwner();
        GObject aim = ((GObject) getAim());
        Hit hit = Hit.createHit(attacker, aim);
        final List<GMod> mods = attacker.getMods();
        for (GMod mod : mods) {
            mod.onHit(hit);
        }
        attacker.getVisualizer().startAttack(aim);
        GameModel.MODEL.log("base", "Hits", attacker, aim);
        aim.takeHit(hit);
    }
}
