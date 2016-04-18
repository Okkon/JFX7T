package sample.Events;

import sample.GameModel;
import sample.Player;
import sample.Tower;


public class TowerOwnerChangeEvent extends GEvent {
    private final Tower tower;
    private final Player player;

    public TowerOwnerChangeEvent(Tower tower, Player player) {
        this.tower = tower;
        this.player = player;
    }

    @Override
    protected void perform() {
        tower.setPlayer(player);
    }

    @Override
    protected void logBeforeEvent() {
        GameModel.MODEL.log("base", "TowerChangeOwner", tower, tower.getPlayer(), player);
    }
}
