package sample;

/**
 * Created by kondrashov on 16.03.2015.
 */
public interface Visualizer {
    void refresh();

    UnitType createUnitChooser();

    void showInfo(Selectable obj);

    void showAction(GAction action);

    void selectObj(Selectable obj);

    void updateTurnNumber();
}
