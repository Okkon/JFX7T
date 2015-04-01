package sample;

/**
 * Created by kondrashov on 24.03.2015.
 */
public interface GFilter {
    public boolean isOk(Selectable obj);

    GObject getObj();

    void setObj(GObject obj);

    boolean check(Selectable obj);

    void setErrorText(String error);

    void setType(FilterFactory.FilterType type);
}
