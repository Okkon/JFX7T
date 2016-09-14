package sample.Skills;


import sample.Core.GUnit;
import sample.Core.Skill;
import sample.Filters.FilterFactory;
import sample.Mods.HolyShield;

public class HolyShieldSkill extends Skill {

    public HolyShieldSkill() {
        addAimFilter(FilterFactory.getFilter(FilterFactory.FilterType.DISTANCE_CHECK, "AimIsTooFar", 60));
    }

    @Override
    public void doAction() {
        ((GUnit) getAim()).getMods().add(new HolyShield());
    }
}
