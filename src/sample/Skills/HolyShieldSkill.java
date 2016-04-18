package sample.Skills;


import sample.Filters.FilterFactory;
import sample.GUnit;
import sample.Mods.HolyShield;
import sample.Selectable;
import sample.Skill;

public class HolyShieldSkill extends Skill {

    public HolyShieldSkill() {
        aimFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.DISTANCE_CHECK, "AimIsTooFar", 60));
    }

    @Override
    public void act(Selectable obj) {
        ((GUnit) obj).getMods().add(new HolyShield());
    }
}
