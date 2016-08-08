package sample.Filters;

import sample.GObject;
import sample.PlaceHaving;

public class IsActingFilter extends AbstractGFilter {
    @Override
    public boolean isOk(PlaceHaving obj) {
        GObject actingUnit = model.getActingUnit();
        return actingUnit == null || obj.equals(actingUnit);
    }
}
