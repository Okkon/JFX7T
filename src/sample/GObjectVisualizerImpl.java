package sample;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GObjectVisualizerImpl extends Label implements GObjectVisualizer {
    private final GamePanel gamePanel;

    public GObjectVisualizerImpl(GObject obj, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setPrefSize(28, 28);
        if (obj instanceof GUnit) {
            GUnit unit = (GUnit) obj;
            getStyleClass().add("unit");
            setText(obj.toString().substring(0, 2));
        }
        if (obj instanceof Tower) {
            Tower tower = (Tower) obj;
            getStyleClass().add("tower");
            setText("T");
        }
        setReady(obj.canAct());
    }

    @Override
    public void changePlace(GameCell currentCell, GameCell cellToGo) {
        final BoardCell fromCell = (BoardCell) currentCell.getVisualizer();
        final BoardCell toCell = (BoardCell) cellToGo.getVisualizer();
        final Bounds bounds = fromCell.getBoundsInParent();
        final Bounds bounds2 = toCell.getBoundsInParent();

        Path path = new Path();
        final double w = bounds.getWidth() / 2;
        final double h = bounds.getHeight() / 2;
        path.getElements().add(new MoveTo(bounds.getMinX() + w, bounds.getMinY() + h));
        path.getElements().add(new LineTo(bounds2.getMinX() + w, bounds2.getMinY() + h));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setNode(GObjectVisualizerImpl.this);
        pathTransition.setPath(path);

        GraphicsHelper.getInstance().addTransition(pathTransition);
    }

    @Override
    public void die(GameCell place) {
        final BoardCell cell = gamePanel.getBoardCell(place);
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(1000));
        transition.setNode(this);
        transition.setFromValue(100);
        transition.setToValue(0);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GraphicsHelper.getInstance().remove(GObjectVisualizerImpl.this);
            }
        });
        GraphicsHelper.getInstance().addTransition(transition);
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

    @Override
    public void create(GameCell gameCell) {
        final BoardCell cell = gamePanel.getBoardCell(gameCell);
        final Bounds b = cell.getBoundsInParent();
        final double w = (b.getWidth() - getPrefWidth()) / 2;
        final double h = (b.getHeight() - getPrefHeight()) / 2;
        this.setTranslateX(b.getMinX() + w);
        this.setTranslateY(b.getMinY() + h);
        GraphicsHelper.getInstance().add(GObjectVisualizerImpl.this);
    }
}
