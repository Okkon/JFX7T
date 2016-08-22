package sample.GActions;

import sample.Core.GObject;
import sample.Core.PlaceHaving;

import java.util.List;

public class RoundAttackAction extends AttackAction {
    private static final RoundAttackAction INSTANCE = new RoundAttackAction();

    private RoundAttackAction() {
    }

    public static RoundAttackAction getInstance() {
        return INSTANCE;
    }

    @Override
    protected List<? extends GObject> getAimsToHit(PlaceHaving aim) {
        return model.getNearUnits(getOwner().getXy());
    }
}
