package sample.Filters;

import sample.GUnit;
import sample.PlaceHaving;

public class CanActFilter extends AbstractGFilter {
    @Override
    public boolean isOk(PlaceHaving obj) {
        return ((GUnit) obj).canAct();
    }
}
