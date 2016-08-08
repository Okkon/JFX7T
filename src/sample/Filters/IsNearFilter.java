package sample.Filters;

import sample.PlaceHaving;

public class IsNearFilter extends AbstractGFilter {
    @Override
    public boolean isOk(PlaceHaving obj) {
        return model.isNear(getObj(), (PlaceHaving) obj);
    }
}
