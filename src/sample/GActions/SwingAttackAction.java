package sample.GActions;

import sample.Core.GObject;
import sample.Core.PlaceHaving;

import java.util.List;

public class SwingAttackAction extends AttackAction {
    private static final SwingAttackAction INSTANCE = new SwingAttackAction();

    private SwingAttackAction() {
    }

    public static SwingAttackAction getInstance() {
        return INSTANCE;
    }

    @Override
    protected List<? extends GObject> getAimsToHit(PlaceHaving aim) {
        return model.getObjectsSurrounding(getOwner().getPlace().getXy(), aim.getXy(), 2);
    }
}
