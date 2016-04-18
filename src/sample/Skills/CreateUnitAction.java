package sample.Skills;

import sample.*;
import sample.GActions.AbstractGAction;

import java.util.List;

import static sample.FilterFactory.FilterType.IS_NEAR;
import static sample.FilterFactory.FilterType.IS_VACANT_CELL;

public class CreateUnitAction extends AbstractGAction {
    private int unitCounter;
    private UnitSelector selector;

    public CreateUnitAction() {
        addAimFilter(IS_VACANT_CELL, "CellIsOccupied");
        addAimFilter(IS_NEAR, "NotNearToMainTower");
        aimType = AimType.Cell;
    }

    @Override
    public boolean canBeSelected() {
        final boolean b = super.canBeSelected();
        return b && unitCounter > 0;
    }

    @Override
    public void onSelect() {
        super.onSelect();
        final List<GUnit> units = getOwner().getPlayer().getAvailableUnits();
        selector = GameModel.MODEL.provideUnitSelector(units);
        selector.setUnitCounter(unitCounter);
    }

    @Override
    public void act(Selectable obj) {
        final GUnit selectedUnit = selector.getSelectedUnit();
        if (selectedUnit != null) {
            final GUnit copy = selectedUnit.copy();
            GameModel.MODEL.createObj(copy, (GameCell) obj);
            unitCounter--;
            selector.close();
        } else {
            GameModel.MODEL.error("errorText", "NoUnitSelected");
        }
    }

    public void setUnitNumber(int unitNumber) {
        this.unitCounter = unitNumber;
    }
}
