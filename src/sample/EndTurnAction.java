package sample;

public class EndTurnAction extends AbstractGAction {
    @Override
    public void onSelect() {
        GameModel.MODEL.endTurn();
    }
}
