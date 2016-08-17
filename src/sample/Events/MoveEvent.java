package sample.Events;

import sample.Core.DefaultMoveAction;
import sample.Core.GEvent;
import sample.Core.GUnit;
import sample.Core.GameCell;

public class MoveEvent extends GEvent {
    private GUnit unit;
    private GameCell toCell;
    private DefaultMoveAction moveAction;

    @Override
    protected void perform() {
        unit.looseMP(moveAction.calculateStepPrice(unit.getPlace(), toCell));
        final ShiftEvent shiftEvent = new ShiftEvent(unit, toCell);
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

    public void setMoveType(DefaultMoveAction moveType) {
        this.moveAction = moveType;
    }
}
