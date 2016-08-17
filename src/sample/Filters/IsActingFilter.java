package sample.Filters;

import sample.Core.GObject;
import sample.Core.PlaceHaving;

public class IsActingFilter extends AbstractGFilter {
    @Override
    public boolean isOk(PlaceHaving obj) {
        GObject actingUnit = model.getActingUnit();
        return actingUnit == null || obj.equals(actingUnit);
    }
}
