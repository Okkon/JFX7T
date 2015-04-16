package sample;

public abstract class Skill extends AbstractGAction {
    public Skill() {
        ownerFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.CAN_ACT, "UnitCantAct"));
        endsTurn = true;
    }

    @Override
    public void perform(Selectable obj) {
        GameModel.MODEL.setLastActedUnit(getOwner());
        super.perform(obj);
    }
}
