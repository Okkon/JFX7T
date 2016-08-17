package sample.Filters;

import sample.Core.PlaceHaving;

public class ClassFilter extends AbstractGFilter {
    private Class clazz;

    @Override
    public boolean isOk(PlaceHaving obj) {
        return model.isNear(getObj(), (PlaceHaving) obj);
    }
}
