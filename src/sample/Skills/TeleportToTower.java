package sample.Skills;

import sample.AimType;
import sample.Filters.FilterFactory;
import sample.GameCell;
import sample.Skill;


public class TeleportToTower extends Skill {
    public TeleportToTower() {
        this.aimType = AimType.Cell;
        addAimFilter(FilterFactory.FilterType.IS_VACANT_CELL, "CellIsOccupied");
        addAimFilter(FilterFactory.FilterType.IS_NEAR_FRIENDLY_TOWER, "NotNearToFriendlyTower");
    }

    @Override
    public void doAction() {
        getOwner().shift((GameCell) getAim());
    }
}
