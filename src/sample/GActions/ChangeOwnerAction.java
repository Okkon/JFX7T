package sample.GActions;

import sample.Core.GObject;
import sample.Core.GameModel;
import sample.Core.Player;
import sample.Events.OwnerChangeEvent;
import sample.Filters.IsObjectFilter;

public class ChangeOwnerAction extends AbstractGAction {
    Player newPlayer;

    public ChangeOwnerAction() {
        addAimFilter(IsObjectFilter.getInstance());
    }

    @Override
    public void doAction() {
        new OwnerChangeEvent(((GObject) getAim()), newPlayer == null ? GameModel.MODEL.getActivePlayer() : newPlayer).process();
    }
}
