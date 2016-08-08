package sample;

import sample.Filters.AbstractGFilter;

public class IsFriendlyFilter extends AbstractGFilter {
    @Override
    public boolean isOk(PlaceHaving obj) {
        return getObj().isFriendlyFor((GObject) obj);
    }
}
