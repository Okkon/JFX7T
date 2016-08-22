package sample.Filters;

import sample.Core.GObject;
import sample.Core.PlaceHaving;

public class IsObjectFilter extends AbstractGFilter {
    private static final IsObjectFilter INSTANCE = new IsObjectFilter();

    private IsObjectFilter() {
    }

    public static IsObjectFilter getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isOk(PlaceHaving obj) {
        return obj instanceof GObject;
    }
}
