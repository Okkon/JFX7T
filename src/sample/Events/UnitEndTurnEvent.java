package sample.Events;

import sample.Core.GEvent;
import sample.Core.GUnit;


public class UnitEndTurnEvent extends GEvent {
    private final GUnit unit;

    public UnitEndTurnEvent(GUnit unit) {
        this.unit = unit;
    }

    @Override
    protected void perform() {
        unit.setMP(0);
        unit.getVisualizer().setReady(false);
    }
}
