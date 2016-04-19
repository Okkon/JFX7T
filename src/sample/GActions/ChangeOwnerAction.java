package sample.GActions;

import sample.Events.OwnerChangeEvent;
import sample.Filters.FilterHelper;
import sample.GObject;
import sample.GameModel;
import sample.Player;

public class ChangeOwnerAction extends AbstractGAction {
    Player newPlayer;

    public ChangeOwnerAction() {
        this.filters.add(FilterHelper.object());
    }

    @Override
    public void doAction() {
        new OwnerChangeEvent(((GObject) getAim()), newPlayer == null ? GameModel.MODEL.getActivePlayer() : newPlayer).process();
    }
}
