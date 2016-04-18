package sample.Filters;

import sample.GObject;
import sample.Selectable;

public class IsObjectFilter extends AbstractGFilter {
    @Override
    public boolean isOk(Selectable obj) {
        return obj instanceof GObject;
    }
}
