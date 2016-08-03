package sample;


import sample.Mods.Invulnerability;

public class MainTower extends Tower {
    @Override
    public boolean canAct() {
        return baseAction.canBeSelected();
    }

    public MainTower() {
        auras.add(new GAura(this, new Invulnerability(), new IsFriendlyFilter()));
    }
}
