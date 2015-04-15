package sample;


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
        super.endHour();
        ((CreateUnitSkill) baseAction).setUnitNumber(1);
    }
}