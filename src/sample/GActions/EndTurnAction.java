package sample.GActions;

import sample.AbstractGAction;
import sample.GameModel;
import sample.Selectable;

public class EndTurnAction extends AbstractGAction {
    @Override
    public void onSelect() {
        GameModel.MODEL.endTurn();
    }

    @Override
    public void act(Selectable obj) {
    }
}
