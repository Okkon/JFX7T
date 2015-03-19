package sample;

import javafx.scene.control.Label;

public class GObjectVisualizerImpl extends Label implements GObjectVisualizer {
    private final GamePanel gamePanel;

    public GObjectVisualizerImpl(GObject obj, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setText(obj.toString());
        getStyleClass().add("unit");
        setTextFill(obj.getPlayer().getColor());
    }

    @Override
    public void changePlace(GameCell currentCell, GameCell cellToGo) {
        BoardCell previousCell = gamePanel.getBoardCell(currentCell);
        BoardCell newCell = gamePanel.getBoardCell(cellToGo);
        previousCell.setCenter(null);
        newCell.setCenter(this);
    }

    @Override
    public void die(GameCell place) {
        final BoardCell cell = gamePanel.getBoardCell(place);
        cell.setCenter(null);
    }
}
