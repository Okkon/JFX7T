package sample.Filters;

import sample.GUnit;
import sample.Selectable;

public class CanActFilter extends AbstractGFilter {
    @Override
    public boolean isOk(Selectable obj) {
        return ((GUnit) obj).canAct();
    }
}
