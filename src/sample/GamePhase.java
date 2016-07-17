package sample;

public class GamePhase extends GPhase {
    @Override
    public void next(GAction action) {
        if (action instanceof Skill) {
            Skill skill = (Skill) action;
            model.setActingUnit(skill.getOwner());
            if (skill.endsTurn() || (skill.getOwner() != null && !skill.getOwner().canAct())) {
                model.endTurn();
            } else if (skill.getOwner() != null) {
                model.select(skill.getOwner());
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
