package sample;


public interface GFilter {
    public boolean isOk(Selectable obj);

    GObject getObj();

    void setObj(GObject obj);

    boolean check(Selectable obj);

    void setErrorText(String error);

    void setType(FilterFactory.FilterType type);
}
