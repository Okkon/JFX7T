package sample;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class GObjectVisualizerImpl extends Label implements GObjectVisualizer {
    private final GamePanel gamePanel;

    public GObjectVisualizerImpl(GObject obj, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setText(obj.toString().substring(0, 2));
        if (obj instanceof GUnit) {
            GUnit unit = (GUnit) obj;
            getStyleClass().add("unit");
        }
        if (obj instanceof Tower) {
            Tower tower = (Tower) obj;
            getStyleClass().add("tower");
        }
        setReady(obj.canAct());
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

    @Override
    public void setPlayer(Player player) {
        final Color color = player.getColor();
        String hex = "#" + Integer.toHexString(color.hashCode());
        setStyle("-fx-background-color: " + hex);
    }

    @Override
    public void setReady(boolean isReady) {
        if (isReady) {
            getStyleClass().add("ready");
        } else {
            getStyleClass().remove("ready");
        }
    }
}
