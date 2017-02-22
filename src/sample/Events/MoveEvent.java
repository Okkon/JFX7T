package sample.Events;

import sample.Core.*;

public class MoveEvent extends GEvent {
    private GUnit unit;
    private GameCell toCell;
    private MoveAction moveAction;

    @Override
    protected void perform() {
        unit.looseMP(moveAction.calculateStepPrice(unit.getPlace(), toCell));
        final ShiftEvent shiftEvent = new ShiftEvent(unit, toCell);
        shiftEvent.process();
    }

    public void setUnit(GUnit unit) {
        this.unit = unit;
    }

    @Override
    protected void logBeforeEvent() {
        GameModel.MODEL.log("base", "UnitMove", unit.getName(), unit.getXy(), toCell.getXy());
    }

    public GUnit getUnit() {
        return unit;
    }

    public void setToCell(GameCell toCell) {
        this.toCell = toCell;
    }

    public void setMoveType(MoveAction moveType) {
        this.moveAction = moveType;
    }
}
