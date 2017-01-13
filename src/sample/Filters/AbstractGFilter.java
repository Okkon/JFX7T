package sample.Filters;


import sample.Core.GFilter;
import sample.Core.GObject;
import sample.Core.GameModel;
import sample.Core.PlaceHaving;
import sample.Helpers.NameHelper;

import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractGFilter<T extends PlaceHaving> implements GFilter<T> {
    private GObject obj;
    private String errorText;
    protected GameModel model = GameModel.MODEL;

    @Override
    public GObject getObj() {
        return obj;
    }

    @Override
    public GFilter setObj(GObject obj) {
        this.obj = obj;
        return this;
    }

    @Override
    public boolean check(T obj) {
        final boolean ok = isOk(obj);
        if (!ok && errorText != null) {
            model.error(errorText);
        }
        return ok;
    }

    @Override
    public GFilter setError(String error) {
        this.errorText = NameHelper.getName("errorText", error);
        return this;
    }

    @Override
    public void filter(Collection<? extends T> collection) {
        Iterator<? extends T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (!isOk(next)) {
                iterator.remove();
            }
        }
    }


}
