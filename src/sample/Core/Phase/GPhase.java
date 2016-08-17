package sample.Core.Phase;

import sample.Core.GAction;
import sample.Core.GameModel;

public abstract class GPhase {
    protected GameModel model = GameModel.MODEL;

    public abstract void next(GAction action);

    public abstract void init();
}
