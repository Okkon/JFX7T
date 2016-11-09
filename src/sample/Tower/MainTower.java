package sample.Tower;


import sample.Core.GAura;
import sample.Core.GameModel;
import sample.Filters.IsFriendlyFilter;
import sample.Filters.IsNearFilter;
import sample.Mods.Invulnerability;

public class MainTower extends Tower {
    @Override
    public boolean canAct() {
        return baseAction.canBeSelected();
    }

    public MainTower() {
        final GAura aura = new GAura(this, new Invulnerability(), IsFriendlyFilter.getInstance(), IsNearFilter.getInstance());
        auras.add(aura);
        GameModel.MODEL.getAuras().add(aura);
    }
}
