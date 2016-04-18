package sample.GActions;

import sample.*;
import sample.Events.OwnerChangeEvent;
import sample.Filters.FilterHelper;

public class ChangeOwnerAction extends AbstractGAction {
    Player newPlayer;

    public ChangeOwnerAction() {
        this.filters.add(FilterHelper.object());
    }

    @Override
    public void act(Selectable obj) {
        new OwnerChangeEvent(((GObject) obj), newPlayer == null ? GameModel.MODEL.getActivePlayer() : newPlayer).process();
    }
}
