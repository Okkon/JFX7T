package sample;

public class CreateAction extends AbstractGAction {
    UnitType type;

    public void act(Selectable aim) {
        if (aim instanceof GameCell) {
            GameCell gameCell = (GameCell) aim;
            GameModel.MODEL.createUnit(type, gameCell);
            GameModel.MODEL.cancel();
        }
    }

    @Override
    public void onSelect() {
        type = GameModel.MODEL.createUnitChooser();
    }

    @Override
    public String getName() {
        return "Create";
    }
}
