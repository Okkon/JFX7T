package sample.Filters;

import sample.Core.GFilter;

import java.util.ArrayList;
import java.util.List;

public class FilterHelper {
    public static List<GFilter> object() {
        List<GFilter> gFilters = new ArrayList<>();
        gFilters.add(IsObjectFilter.getInstance());
        return gFilters;
    }

    public static List<GFilter> gameCell() {
        List<GFilter> gFilters = new ArrayList<>();
        gFilters.add(VacantCellFilter.getInstance());
        return gFilters;
    }
}
