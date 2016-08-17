package sample.Filters;


import sample.Core.GFilter;
import sample.Core.GObject;
import sample.Core.GameModel;
import sample.Core.PlaceHaving;
import sample.Helpers.NameHelper;

public abstract class AbstractGFilter implements GFilter {
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
    public boolean check(PlaceHaving obj) {
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


}
