package sample.Tower;

import sample.Core.AimType;
import sample.Core.GameCell;
import sample.Core.Skill;
import sample.Filters.VacantCellFilter;


public class TeleportToTower extends Skill {
    public TeleportToTower() {
        this.aimType = AimType.Cell;
        addAimFilter(VacantCellFilter.getInstance().setError("CellIsOccupied"));
        addAimFilter(NearFriendlyTowerFilter.getInstance().setError("NotNearToFriendlyTower"));
    }

    @Override
    public void doAction() {
       getActor().shift((GameCell) getAim());
    }
}
