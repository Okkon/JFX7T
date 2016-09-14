package sample.GActions;


import sample.Core.AimType;
import sample.Core.GFilter;
import sample.Core.GUnit;
import sample.Core.GameModel;
import sample.Filters.BelongsToActivePlayerFilter;
import sample.Filters.CanActFilter;
import sample.Filters.FilterFactory;
import sample.Filters.IsActingFilter;

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
        model.showInfo(getAim());
        boolean isOk = true;
        for (GFilter preferableAimFilter : preferableAimFilters) {
            if (!preferableAimFilter.isOk(getAim())) {
                isOk = false;
                break;
            }
        }
        if (isOk) {
            GameModel.MODEL.select(getAim());
        }
    }

    private SelectAction() {
        aimType = AimType.Object;
        addAimFilter(new FilterFactory.ClassFilter().setClass(GUnit.class));
        addPreferableAimFilter(new BelongsToActivePlayerFilter());
        addAimFilter(new CanActFilter().setError("UnitCantAct"));
        addAimFilter(new IsActingFilter().setError("OtherUnitSelected"));
    }

    @Override
    protected boolean needsLogging() {
        return false;
    }


}
