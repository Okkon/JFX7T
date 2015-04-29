package sample.GActions;


import sample.FilterFactory;
import sample.GUnit;
import sample.Mods.HolyShield;
import sample.Selectable;
import sample.Skill;

public class HolyShieldSkill extends Skill {

    public HolyShieldSkill() {
        super();
        final FilterFactory.DistanceFilter filter = (FilterFactory.DistanceFilter) FilterFactory.getFilter(FilterFactory.FilterType.DISTANCE_CHECK, "AimIsTooFar");
        filter.setDistance(60);
        aimFilters.add(filter);

    }

    @Override
    public void act(Selectable obj) {
        ((GUnit) obj).getMods().add(new HolyShield());
    }
}
