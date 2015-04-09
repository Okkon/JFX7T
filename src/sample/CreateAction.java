package sample;

public class CreateAction extends AbstractGAction {
    GObject obj;

    public void act(Selectable aim) {
        if (aim instanceof GameCell) {
            GameCell gameCell = (GameCell) aim;
            GameModel.MODEL.createObj(obj, gameCell);
            obj.setPlayer(obj.getPlayer());
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
