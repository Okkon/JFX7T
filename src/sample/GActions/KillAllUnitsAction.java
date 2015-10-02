package sample.GActions;

import sample.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KillAllUnitsAction extends AbstractGAction {
    @Override
    public void onSelect() {
        final List<GObject> units = new ArrayList<GObject>();
        units.addAll(GameModel.MODEL.getObjects(Collections.singleton(FilterFactory.getFilter(FilterFactory.FilterType.IS_UNIT))));
        for (GObject unit : units) {
            unit.die(new Hit());
        }
        super.onSelect();
    }

    @Override
    public void act(Selectable obj) {

    }

}
