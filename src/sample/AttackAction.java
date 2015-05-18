package sample;

import java.util.List;

public class AttackAction extends Skill {
    public static final AttackAction DEFAULT = new AttackAction();

    private AttackAction() {
        super();
        aimFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.IS_NEAR, "AimIsTooFar"));
        aimFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.IS_UNIT, "NotUnit"));
        aimFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.CAN_BE_ATTACKED, "CanAttack"));
    }

    @Override
    public void act(Selectable obj) {
        GUnit attacker = (GUnit) getOwner();
        GObject aim = ((GObject) obj);
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
