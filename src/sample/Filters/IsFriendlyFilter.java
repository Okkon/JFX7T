package sample.Filters;

import sample.Core.GObject;
import sample.Core.PlaceHaving;

public class IsFriendlyFilter extends AbstractGFilter {
    @Override
    public boolean isOk(PlaceHaving obj) {
        return getObj().isFriendlyFor((GObject) obj);
    }
}
