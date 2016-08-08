package sample.Filters;

import sample.GObject;
import sample.PlaceHaving;

public class IsObjectFilter extends AbstractGFilter {
    @Override
    public boolean isOk(PlaceHaving obj) {
        return obj instanceof GObject;
    }
}
