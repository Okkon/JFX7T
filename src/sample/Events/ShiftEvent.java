package sample.Events;

import sample.Core.GEvent;
import sample.Core.GObject;
import sample.Core.GameCell;
import sample.Core.GameModel;

public class ShiftEvent extends GEvent {
    private GameCell startCell;
    private GameCell finishCell;
    private GObject object;

    public ShiftEvent(GObject object, GameCell toCell) {
        this.object = object;
        this.startCell = object.getPlace();
        this.finishCell = toCell;
        addChecker(UnitAliveChecker.getInstance());
    }

    public void setStartCell(GameCell startCell) {
        this.startCell = startCell;
    }

    public void setFinishCell(GameCell finishCell) {
        this.finishCell = finishCell;
    }

    public void setObject(GObject object) {
        this.object = object;
        setStartCell(object.getPlace());
    }

    @Override
    protected void visualize() {
        object.getVisualizer().changePlace(startCell, finishCell);
    }

    @Override
    protected void logBeforeEvent() {
        GameModel.MODEL.log("base", "ObjectShift", object.getName(), startCell, finishCell);
    }

    @Override
    protected void perform() {
        startCell.setObj(null);
        object.setPlace(finishCell);
        if (object.isAlive()) {
            finishCell.setObj(object);
        }
    }

    public GObject getObject() {
        return object;
    }
}
