package sample.Tower;

import sample.Core.AimType;
import sample.Core.GameCell;
import sample.Core.Skill;
import sample.Filters.VacantCellFilter;


public class TeleportToTower extends Skill {
    public TeleportToTower() {
        this.aimType = AimType.Cell;
        getAimFilters().add(VacantCellFilter.getInstance().setError("CellIsOccupied"));
        getAimFilters().add(NearFriendlyTowerFilter.getInstance().setError("NotNearToFriendlyTower"));
    }

    @Override
    public void doAction() {
        getOwner().shift((GameCell) getAim());
    }
}
