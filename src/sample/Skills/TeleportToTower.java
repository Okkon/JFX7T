package sample.Skills;

import sample.*;
import sample.Filters.FilterFactory;


public class TeleportToTower extends Skill {
    public TeleportToTower() {
        this.aimType = AimType.Cell;
        addAimFilter(FilterFactory.FilterType.IS_VACANT_CELL, "CellIsOccupied");
        addAimFilter(FilterFactory.FilterType.IS_NEAR_FRIENDLY_TOWER, "NotNearToFriendlyTower");
    }

    @Override
    public void act(Selectable obj) {
        getOwner().shift((GameCell) obj);
    }
}
