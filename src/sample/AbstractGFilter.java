package sample;


public abstract class AbstractGFilter implements GFilter {
    private GObject obj;
    private FilterFactory.FilterType type;
    private String errorText;

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
            GameModel.MODEL.error(errorText);
        }
        return ok;
    }

    @Override
    public void setErrorText(String error) {
        this.errorText = error;
    }

    @Override
    public void setType(FilterFactory.FilterType type) {
        this.type = type;
    }
}
