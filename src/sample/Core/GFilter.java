package sample.Core;


import java.util.Collection;

public interface GFilter {
    boolean isOk(PlaceHaving obj);

    GObject getObj();

    GFilter setObj(GObject obj);

    boolean check(PlaceHaving obj);

    GFilter setError(String error);

    /**
     * removes elements from collection, which isOk method returns false value
     *
     * @param collection
     */
    void filter(Collection<? extends PlaceHaving> collection);
}
