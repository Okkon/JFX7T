package sample;

import java.util.List;

public interface MainVisualizer {
    void refresh();

    GObject createUnitCreationPanel();

    void showObjInfo(Selectable obj);

    void showAction(GAction action);

    void showObjName(Selectable obj);

    void showTurnNumber();

    void log(String s);

    void showActivePlayer();

    void createVisualizerFor(GObject obj);

    void error(String s);

    UnitSelector createUnitSelector(List<GUnit> units);

    void showLastActedUnit(GObject unit);
}
