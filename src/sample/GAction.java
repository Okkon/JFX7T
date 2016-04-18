package sample;


import java.util.List;

public interface GAction {

    boolean canSelect(Selectable obj);

    boolean canBeSelected();

    public String getName();

    GObject getOwner();

    void setOwner(GObject owner);

    void act(Selectable aim);

    void onSelect();

    void perform(Selectable obj);

    String getDescription();

    List<? extends PlaceHaving> getAims();

    int estimate(PlaceHaving aim);

    void tryToSelect(PlaceHaving obj);
}
