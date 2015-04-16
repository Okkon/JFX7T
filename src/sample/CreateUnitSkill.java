package sample;

import java.util.List;


public class CreateUnitSkill extends AbstractGAction {
    int unitCounter;
    UnitSelector selector;


    public CreateUnitSkill(int unitNumber) {
        aimFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.IS_VACANT_CELL, "CellIsOccupied"));
        aimFilters.add(FilterFactory.getFilter(FilterFactory.FilterType.IS_NEAR, "NotNearToMainTower"));
        unitCounter = unitNumber;
    }

    @Override
    public boolean canBeSelected() {
        final boolean b = super.canBeSelected();
        return b && unitCounter > 0;
    }

    @Override
    public void onSelect() {
        final List<GUnit> units = getOwner().getPlayer().getAvailableUnits();
        selector = GameModel.MODEL.provideUnitSelector(units);
    }

    @Override
    public void act(Selectable obj) {
        super.act(obj);
        final GUnit selectedUnit = selector.getSelectedUnit();
        if (selectedUnit != null) {
            final GUnit copy = selectedUnit.copy();
            GameModel.MODEL.createObj(copy, (GameCell) obj);
            unitCounter--;
            if (unitCounter < 1) {
                selector.close();
            }
        } else {
            GameModel.MODEL.error("errorText", "NoUnitSelected");
        }
    }

    public void setUnitNumber(int unitNumber) {
        this.unitCounter = unitNumber;
    }
}
