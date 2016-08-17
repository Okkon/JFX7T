package sample.Core;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GAura<H extends PlaceHaving, A extends PlaceHaving> {
    private GMod mod;
    private H holder;
    private List<GFilter> filters = new ArrayList<>();

    public GAura(H holder, GMod mod, GFilter... filters) {
        this.holder = holder;
        this.mod = mod;
        Collections.addAll(this.filters, filters);
    }

    public boolean validFor(A obj) {
        for (GFilter filter : filters) {
            filter.setObj((GObject) holder);
            if (!filter.isOk(obj)) {
                return false;
            }
        }
        return true;
    }

    public GMod getMod() {
        return mod;
    }
}
