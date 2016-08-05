package sample.Filters;


import sample.GObject;
import sample.Selectable;

public interface GFilter {
    boolean isOk(Selectable obj);

    GObject getObj();

    GFilter setObj(GObject obj);

    boolean check(Selectable obj);

    GFilter setError(String error);

}
