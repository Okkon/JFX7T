package sample;

/**
 * Created by kondrashov on 16.03.2015.
 */
public interface Visualizer {
    void refresh();

    GObject createUnitCreationPanel();

    void showInfo(Selectable obj);

    void showAction(GAction action);

    void selectObj(Selectable obj);

    void updateTurnNumber();

    void log(String s);
}
