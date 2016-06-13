package sample;

public abstract class GPhase {
    protected GameModel model = GameModel.MODEL;

    public abstract void next(GAction action);

    public abstract void init();
}
