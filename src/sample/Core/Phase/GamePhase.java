package sample.Core.Phase;

import sample.Core.GAction;
import sample.Core.Skill;
import sample.Graphics.GraphicsHelper;

public class GamePhase extends GPhase {
    @Override
    public void next(GAction action) {
        if (action instanceof Skill) {
            Skill skill = (Skill) action;
           model.setActingUnit(skill.getActor());
           if (skill.endsTurn() || (skill.getActor() != null && !skill.getActor().canAct())) {
                model.endTurn();
           } else if (skill.getActor() != null) {
              model.select(skill.getActor());
            }
        }
        GraphicsHelper.getInstance().play();
    }

    @Override
    public void init() {
        model.setPhaseAction(model.SELECT_ACTION);
        model.startHour();
    }
}
