package sample;

public class GamePhase extends GPhase {
    @Override
    public void next(GAction action) {

    }

    @Override
    public void init() {
        GameModel.CURRENT_PHASE_ACTION = GameModel.SELECT_ACTION;
        GameModel.MODEL.startHour();
    }
}
