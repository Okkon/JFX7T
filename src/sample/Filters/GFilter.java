package sample.Filters;


import sample.GObject;
import sample.Selectable;

public interface GFilter {
    boolean isOk(Selectable obj);

    GObject getObj();

    void setObj(GObject obj);

    boolean check(Selectable obj);

    GFilter setErrorText(String error);

    void setType(FilterFactory.FilterType type);
}
