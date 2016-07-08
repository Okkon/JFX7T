package sample;

import sample.GActions.AbstractGAction;

public class EndHourAction extends AbstractGAction {
    @Override
    public void doAction() {
        model.endHour();
    }
}
