package sample;

public abstract class Skill extends AbstractGAction {
    public Skill() {
        ownerFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.CAN_ACT));
        ownerFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.BELONG_TO_PLAYER));
        endsTurn = true;
    }
}
