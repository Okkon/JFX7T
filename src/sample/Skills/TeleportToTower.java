package sample.Skills;

import sample.AimType;
import sample.Filters.FilterFactory;
import sample.Filters.IsVacantCellFilter;
import sample.GameCell;
import sample.Skill;


public class TeleportToTower extends Skill {
    public TeleportToTower() {
        this.aimType = AimType.Cell;
        getAimFilters().add(new IsVacantCellFilter().setError("CellIsOccupied"));
        addAimFilter(FilterFactory.FilterType.IS_NEAR_FRIENDLY_TOWER, "NotNearToFriendlyTower");
    }

    @Override
    public void doAction() {
        getOwner().shift((GameCell) getAim());
    }
}
