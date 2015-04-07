package sample;

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
}
