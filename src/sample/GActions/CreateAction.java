package sample.GActions;

import sample.GObject;
import sample.GameCell;
import sample.GameModel;

public class CreateAction extends AbstractGAction {
    GObject obj;

    public void doAction() {
        if (getAim() instanceof GameCell) {
            GameCell gameCell = (GameCell) getAim();
            GameModel.MODEL.createObj(obj, gameCell);
            GameModel.MODEL.cancel();
        }
    }

    @Override
    public void onSelect() {
        obj = GameModel.MODEL.createUnitCreationPanel();
    }

    @Override
    public String getName() {
        return "Create";
    }
}
