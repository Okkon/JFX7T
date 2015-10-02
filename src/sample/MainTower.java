package sample;


import sample.Skills.CreateUnitAction;

public class MainTower extends Tower {

    @Override
    public boolean canAct() {
        return baseAction.canBeSelected();
    }

    public MainTower() {
        super();
        final CreateUnitAction createUnitAction = new CreateUnitAction();
        createUnitAction.setUnitNumber(1);
        createUnitAction.setOwner(this);
        baseAction = createUnitAction;
        skills.add(createUnitAction);
    }

    @Override
    public void endHour() {
        super.endHour();
        ((CreateUnitAction) baseAction).setUnitNumber(GameModel.MODEL.isTheWeakestPlayer(getPlayer()) ? 2 : 1);
    }
}
