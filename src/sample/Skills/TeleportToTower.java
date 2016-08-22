package sample.Skills;

import sample.Core.AimType;
import sample.Core.GameCell;
import sample.Core.Skill;
import sample.Filters.FilterFactory;
import sample.Filters.VacantCellFilter;


public class TeleportToTower extends Skill {
    public TeleportToTower() {
        this.aimType = AimType.Cell;
        getAimFilters().add(VacantCellFilter.getInstance().setError("CellIsOccupied"));
        addAimFilter(FilterFactory.FilterType.IS_NEAR_FRIENDLY_TOWER, "NotNearToFriendlyTower");
    }

    @Override
    public void doAction() {
        getOwner().shift((GameCell) getAim());
    }
}
