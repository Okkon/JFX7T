package sample;


public class SelectAction extends AbstractGAction {
    @Override
    public void act(Selectable obj) {
        GameModel.MODEL.select(obj);
    }
}
