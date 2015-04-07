package sample;

import javafx.scene.layout.BorderPane;

public class BoardCell extends BorderPane implements Visualizer {
    private static final String CAN_BE_SELECTED = "canBeSelected";
    private GameCell cell;

    public BoardCell(GameCell cell) {
        this.cell = cell;
        this.getStyleClass().add("cell");
        cell.setVisualizer(this);
    }


    public XY getXy() {
        return cell.getXy();
    }

    @Override
    public void setSelectionPossibility(boolean b) {
        if (b) {
            getStyleClass().add(CAN_BE_SELECTED);
        } else {
            getStyleClass().remove(CAN_BE_SELECTED);
        }
    }

    public GameCell getGameCell() {
        return cell;
    }
}
