package sample.Core;

import java.util.List;

public interface MainVisualizer {
    void refresh();

    void showObjInfo(Selectable obj);

    void showAction(GAction action);

    void showTurnNumber();

    void log(String s);

    void showActivePlayer();

    void createVisualizerFor(GObject obj);

    void error(String s);

    UnitSelector createUnitSelector(List<GUnit> units);

}
