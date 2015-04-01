package sample;

public abstract class Skill extends AbstractGAction {
    public Skill() {
        ownerFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.CAN_ACT, "Unit can't act!"));
        ownerFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.BELONG_TO_PLAYER, "Unit doesn't belong to active player!"));
        endsTurn = true;
    }
}
