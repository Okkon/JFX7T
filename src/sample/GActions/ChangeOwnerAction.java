package sample.GActions;

import sample.Core.GObject;
import sample.Core.GameModel;
import sample.Core.Player;
import sample.Events.OwnerChangeEvent;
import sample.Filters.FilterHelper;

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
