package sample.Filters;

import sample.Core.GUnit;
import sample.Core.PlaceHaving;

public class CanActFilter extends AbstractGFilter {
    @Override
    public boolean isOk(PlaceHaving obj) {
        return ((GUnit) obj).canAct();
    }
}
