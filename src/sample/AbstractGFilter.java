package sample;


public abstract class AbstractGFilter implements GFilter {
    private GObject obj;

    @Override
    public GObject getObj() {
        return obj;
    }

    @Override
    public void setObj(GObject obj) {
        this.obj = obj;
    }
}
