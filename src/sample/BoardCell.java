package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class BoardCell extends BorderPane implements CellVisualizer {
    public static final String SELECTED = "selected";
    private static final String CAN_BE_SELECTED = "canBeSelected";
    private GameCell cell;
    private static BoardCell lastSelected;

    public BoardCell(GameCell cell) {
        this.cell = cell;
        this.getStyleClass().add("borderPane");
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
        setCenter(null);
        final GObject obj = cell.getObj();
        if (obj != null) {
            final Label label = new Label(obj.toString());
            label.getStyleClass().add("unit");
            label.setTextFill(obj.getPlayer().getColor());
            setCenter(label);
        }
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
