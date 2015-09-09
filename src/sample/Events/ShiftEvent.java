package sample.Events;

import sample.GEvent;
import sample.GObject;
import sample.GameCell;
import sample.GameModel;

public class ShiftEvent extends GEvent {
    private GameCell startCell;
    private GameCell finishCell;
    private GObject object;

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
}
