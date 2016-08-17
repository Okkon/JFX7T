package sample.Core;


public interface GFilter {
    boolean isOk(PlaceHaving obj);

    GObject getObj();

    GFilter setObj(GObject obj);

    boolean check(PlaceHaving obj);

    GFilter setError(String error);

}
