package sample.Skills;

import sample.GActions.AbstractGAction;
import sample.GameModel;
import sample.Selectable;

public class EndTurnAction extends AbstractGAction {
    public EndTurnAction() {
        endsTurn = true;
    }

    @Override
    public void onSelect() {
        GameModel.MODEL.endTurn();
    }

    @Override
    public void act(Selectable obj) {
    }
}
