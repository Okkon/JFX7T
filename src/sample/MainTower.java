package sample;


import sample.GActions.CreateUnitAction;

public class MainTower extends Tower {
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
        ((CreateUnitAction) baseAction).setUnitNumber(1);
    }
}
