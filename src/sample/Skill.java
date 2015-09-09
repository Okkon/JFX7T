package sample;

public abstract class Skill extends AbstractGAction {

    public Skill() {
        ownerFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.CAN_ACT, "UnitCantAct"));
        endsTurn = true;
    }

    @Override
    protected void afterPerform() {
        super.afterPerform();
        GameModel.MODEL.setActingUnit(endsTurn ? null : getOwner());
    }
}
