package sample;

/**
 * Created by kondrashov on 17.03.2015.
 */
public class EndTurnAction extends AbstractGAction {
    @Override
    public void onSelect() {
        GameModel.MODEL.endTurn();
    }
}
