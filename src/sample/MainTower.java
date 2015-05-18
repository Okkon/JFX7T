package sample;


import sample.GActions.CreateUnitSkill;

public class MainTower extends Tower {
    public MainTower() {
        super();
        final CreateUnitSkill createUnitSkill = new CreateUnitSkill(1);
        createUnitSkill.setOwner(this);
        baseAction = createUnitSkill;
        skills.add(createUnitSkill);
    }

    @Override
    public void endHour() {
        ((CreateUnitSkill) baseAction).setUnitNumber(1);
    }
}
