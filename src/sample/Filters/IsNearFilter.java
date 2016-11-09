package sample.Filters;

import sample.Core.PlaceHaving;
import sample.XY;

public class IsNearFilter extends AbstractGFilter {
    private static IsNearFilter INSTANCE = IsNearFilter.getInstance();

    private IsNearFilter() {
    }

    ;

    public static IsNearFilter getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isOk(PlaceHaving obj) {
        return XY.isNear(getObj().getXy(), obj.getXy());
    }
}
