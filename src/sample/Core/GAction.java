package sample.Core;


import java.util.List;

public interface GAction {

    boolean canSelect(PlaceHaving obj);

    boolean canBeSelected();

    String getName();

    GObject getOwner();

    void setOwner(GObject owner);

    void doAction();

    void onSelect();

    void perform();

    String getDescription();

    List<? extends PlaceHaving> getPossibleAims();

    int estimate(PlaceHaving aim);

    void tryToSelect(PlaceHaving obj);

    List<PlaceHaving> getAims();

    void cancel();
}

