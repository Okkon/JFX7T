package sample.Filters;

import java.util.ArrayList;
import java.util.List;

public class FilterHelper {
    public static List<GFilter> object() {
        List<GFilter> gFilters = new ArrayList<GFilter>();
        gFilters.add(new IsObjectFilter());
        return gFilters;
    }
}
