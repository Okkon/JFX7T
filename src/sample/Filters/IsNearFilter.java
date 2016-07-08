package sample.Filters;

import sample.PlaceHaving;
import sample.Selectable;

public class IsNearFilter extends AbstractGFilter {
    @Override
    public boolean isOk(Selectable obj) {
        return model.isNear(getObj(), (PlaceHaving) obj);
    }
}
