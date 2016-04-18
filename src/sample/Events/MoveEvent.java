package sample.Events;

import sample.GUnit;
import sample.GameCell;

public class MoveEvent extends GEvent {
    private GUnit unit;
    private GameCell toCell;
    private int stepPrice;

    @Override
    protected void perform() {
        unit.looseMP(stepPrice);
        final ShiftEvent shiftEvent = new ShiftEvent(unit, toCell);
        shiftEvent.setPredecessor(this);
        shiftEvent.process();
    }

    public void setUnit(GUnit unit) {
        this.unit = unit;
    }

    public GUnit getUnit() {
        return unit;
    }

    public void setToCell(GameCell toCell) {
        this.toCell = toCell;
    }

    public GameCell getToCell() {
        return toCell;
    }

    public void setStepPrice(int stepPrice) {
        this.stepPrice = stepPrice;
    }
}
