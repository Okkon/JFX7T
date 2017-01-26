package sample.Core;


import java.util.List;

public interface GAction {

    boolean canSelect(PlaceHaving obj);

    boolean canBeSelected();

    String getName();

   GObject getActor();

   void setActor(GObject actor);

    void doAction();

    void onSelect();

    void perform();

    String getDescription();

    List<? extends PlaceHaving> getPossibleAims();

    int estimate(PlaceHaving aim);

   void tryToSelectAction(PlaceHaving obj);

    List<PlaceHaving> getAims();

    void cancel();
}

