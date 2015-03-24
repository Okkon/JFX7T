package sample;

/**
 * Created by kondrashov on 24.03.2015.
 */
public interface GFilter {
    public boolean isOk(Selectable obj);

    GObject getObj();

    void setObj(GObject obj);
}
