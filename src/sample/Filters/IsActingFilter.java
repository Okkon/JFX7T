package sample.Filters;

import sample.GObject;
import sample.Selectable;

public class IsActingFilter extends AbstractGFilter {
    @Override
    public boolean isOk(Selectable obj) {
        GObject actingUnit = model.getActingUnit();
        return actingUnit == null || obj.equals(actingUnit);
    }
}
