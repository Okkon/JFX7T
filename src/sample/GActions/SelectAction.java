package sample.GActions;


import sample.Core.AimType;
import sample.Core.GUnit;
import sample.Core.GameModel;
import sample.Filters.CanActFilter;
import sample.Filters.FilterFactory;
import sample.Filters.IsActingFilter;

import static sample.Filters.FilterFactory.FilterType.BELONG_TO_PLAYER;
import static sample.Filters.FilterFactory.getFilters;

public class SelectAction extends AbstractGAction {
    private static SelectAction INSTANCE;

    @Override
    public void onSelect() {
        model.select(null);
        super.onSelect();
    }

    public static SelectAction getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SelectAction();
        }
        return INSTANCE;
    }

    @Override
    public void doAction() {
        GameModel.MODEL.select(getAim());
    }

    private SelectAction() {
        aimType = AimType.Object;
        this.getAimFilters().addAll(getFilters(BELONG_TO_PLAYER));
        getAimFilters().add(new FilterFactory.ClassFilter().setClass(GUnit.class));
        getAimFilters().add(new CanActFilter().setError("UnitCantAct"));
        getAimFilters().add(new IsActingFilter().setError("OtherUnitSelected"));
    }

    @Override
    protected boolean needsLogging() {
        return false;
    }


}
