package sample.Skills;

import sample.Core.Skill;

public class EndTurnAction extends Skill {
    private static final EndTurnAction INSTANCE = new EndTurnAction();

    private EndTurnAction() {
    }

    public static EndTurnAction getInstance() {
        return INSTANCE;
    }

    @Override
    public void doAction() {
    }
}
