package sample.Filters;

import sample.Core.GObject;
import sample.Core.PlaceHaving;

public class IsFriendlyFilter extends AbstractGFilter {
    private static IsFriendlyFilter INSTANCE = IsFriendlyFilter.getInstance();

    private IsFriendlyFilter() {
    }

    ;

    public static IsFriendlyFilter getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isOk(PlaceHaving obj) {
        return getObj().isFriendlyFor((GObject) obj);
    }
}
