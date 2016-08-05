package sample.Skills;
import sample.*;
import sample.Filters.GFilter;
import sample.Filters.IsNearFilter;
import sample.Filters.IsVacantCellFilter;
import sample.GActions.AbstractGAction;

import java.util.List;

public class CreateUnitAction extends AbstractGAction {
    private int unitCounter;
    private UnitSelector selector;
    private Player player;

    public CreateUnitAction() {
        getAimFilters().add(new IsVacantCellFilter().setError("CellIsOccupied"));
        getAimFilters().add(new IsNearFilter().setError("NotNearToMainTower"));
        aimType = AimType.Cell;
    }

    @Override
    public boolean canBeSelected() {
        final boolean b = super.canBeSelected();
        return b && unitCounter > 0;
    }

    @Override
    public void cancel() {
        selector.close();
        selector = null;
        super.cancel();
    }

    @Override
    public void onSelect() {
        super.onSelect();
        GFilter filter = findFilterByClass(IsNearFilter.class);
        if (filter != null) {
            filter.setObj(TowerHelper.getPlayersMainTower(model.getActivePlayer()));
        }
        if (unitCounter > 0 && selector == null) {
            final List<GUnit> units = getPlayer().getAvailableUnits();
            selector = GameModel.MODEL.provideUnitSelector(units);
            selector.setUnitCounter(unitCounter);
        }
    }

    @Override
    public void doAction() {
        final GUnit selectedUnit = selector.getSelectedUnit();
        if (selectedUnit != null) {
            final GUnit copy = selectedUnit.copy();
            GameModel.MODEL.createObj(copy, (GameCell) getAim());
            unitCounter--;
            selector.setUnitCounter(unitCounter);
            if (unitCounter <= 0) {
                selector.close();
            }
        } else {
            GameModel.MODEL.error("errorText", "NoUnitSelected");
        }
        GraphicsHelper.getInstance().play();
    }

    public void setUnitNumber(int unitNumber) {
        this.unitCounter = unitNumber;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player == null ? GameModel.MODEL.getActivePlayer() : player;
    }

    public int getUnitCount() {
        return unitCounter;
    }
}
