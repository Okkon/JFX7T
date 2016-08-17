package sample.Filters;

import sample.Core.GObject;
import sample.Core.PlaceHaving;

public class IsObjectFilter extends AbstractGFilter {
    @Override
    public boolean isOk(PlaceHaving obj) {
        return obj instanceof GObject;
    }
}
