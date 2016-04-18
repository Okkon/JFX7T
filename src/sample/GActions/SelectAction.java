package sample.GActions;


import sample.AimType;
import sample.GameModel;
import sample.Selectable;

import static sample.Filters.FilterFactory.FilterType.*;
import static sample.Filters.FilterFactory.getFilters;

public class SelectAction extends AbstractGAction {
    @Override
    public void act(Selectable obj) {
        GameModel.MODEL.select(obj);
    }

    public SelectAction() {
        aimType = AimType.Object;
        this.aimFilters.addAll(getFilters(IS_UNIT, CAN_ACT, BELONG_TO_PLAYER));
    }

    @Override
    protected boolean needsLogging() {
        return false;
    }


}
