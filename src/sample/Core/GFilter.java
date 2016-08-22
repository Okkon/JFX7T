package sample.Core;


import java.util.Collection;

public interface GFilter {
    boolean isOk(PlaceHaving obj);

    GObject getObj();

    GFilter setObj(GObject obj);

    boolean check(PlaceHaving obj);

    GFilter setError(String error);

    Collection<? extends PlaceHaving> filter(Collection<? extends PlaceHaving> obj);
}
