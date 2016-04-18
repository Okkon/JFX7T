package sample.Skills;

import sample.GameModel;
import sample.Selectable;
import sample.Skill;

public class EndTurnAction extends Skill {
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
