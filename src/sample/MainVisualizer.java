package sample;

public interface MainVisualizer {
    void refresh();

    GObject createUnitCreationPanel();

    void showInfo(Selectable obj);

    void showAction(GAction action);

    void selectObj(Selectable obj);

    void showTurnNumber();

    void log(String s);

    void showActivePlayer();

    void createVisualizerFor(GObject obj);

    void error(String s);
}
