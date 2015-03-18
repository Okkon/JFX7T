package sample;

/**
 * Created by kondrashov on 17.03.2015.
 */
public class EndHourAction extends AbstractGAction {
    @Override
    public void onSelect() {
        GameModel.MODEL.endHour();
    }
}
