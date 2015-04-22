package sample;


import java.util.Collection;
import java.util.List;

import static sample.FilterFactory.FilterType.*;
import static sample.FilterFactory.getFilters;

public class SelectAction extends AbstractGAction {
    @Override
    public void act(Selectable obj) {
        GameModel.MODEL.select(obj);
    }

    @Override
    public void onSelect() {
        super.onSelect();
        final GameModel model = GameModel.MODEL;
        final Collection<GFilter> filters = getFilters(IS_UNIT, CAN_ACT, BELONG_TO_PLAYER);
        final List<GObject> objects = model.getObjects(filters);
        model.showSelectionPossibility(objects);
    }


}
