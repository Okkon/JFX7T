package sample;

import javafx.scene.layout.BorderPane;

public class BoardCell extends BorderPane implements CellVisualizer {
    public static final String SELECTED = "selected";
    private static final String CAN_BE_SELECTED = "canBeSelected";
    private GameCell cell;
    private static BoardCell lastSelected;

    public BoardCell(GameCell cell) {
        this.cell = cell;
        this.getStyleClass().add("cell");
        cell.setCellVisualizer(this);
    }

    public static BoardCell getLastSelected() {
        return lastSelected;
    }

    public XY getXy() {
        return cell.getXy();
    }

    @Override
    public void visualize() {

    }

    @Override
    public void showSelectionPossibility() {
        getStyleClass().add(CAN_BE_SELECTED);
    }

    @Override
    public void hideSelectionPossibility() {
        getStyleClass().remove(CAN_BE_SELECTED);
    }

    public GameCell getGameCell() {
        return cell;
    }

    public void deselect() {
        getStyleClass().remove(SELECTED);
    }

    public void select() {
        lastSelected = this;
        getStyleClass().add(SELECTED);
    }
}
