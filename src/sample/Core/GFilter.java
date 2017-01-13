package sample.Core;


import java.util.Collection;

public interface GFilter<T extends PlaceHaving> {
    boolean isOk(T obj);

    GObject getObj();

    GFilter setObj(GObject obj);

    boolean check(T obj);

    GFilter setError(String error);

    /**
     * removes elements from collection, which isOk method returns false value
     *
     * @param collection
     */
    void filter(Collection<? extends T> collection);
}
