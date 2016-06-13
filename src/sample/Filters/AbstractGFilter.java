package sample.Filters;


import sample.GObject;
import sample.GameModel;
import sample.NameHelper;
import sample.Selectable;

public abstract class AbstractGFilter implements GFilter {
    private GObject obj;
    private FilterFactory.FilterType type;
    private String errorText;
    protected GameModel model = GameModel.MODEL;

    @Override

    public GObject getObj() {
        return obj;
    }

    @Override
    public void setObj(GObject obj) {
        this.obj = obj;
    }

    @Override
    public boolean check(Selectable obj) {
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
    public void setType(FilterFactory.FilterType type) {
        this.type = type;
    }
}
