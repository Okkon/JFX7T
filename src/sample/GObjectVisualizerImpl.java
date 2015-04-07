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

public class GObjectVisualizerImpl implements GObjectVisualizer {
    private final GamePanel gamePanel;
    private final Label token = new Label();

    public GObjectVisualizerImpl(GObject obj, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        final int size = MyConst.OBJECT_VISUALIZER_SIZE;
        token.setPrefSize(size, size);
        if (obj instanceof GUnit) {
            GUnit unit = (GUnit) obj;
            token.getStyleClass().add("unit");
            token.setText(obj.toString().substring(0, 2));
        }
        if (obj instanceof Tower) {
            Tower tower = (Tower) obj;
            token.getStyleClass().add("tower");
            token.setText("T");
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
        pathTransition.setDuration(Duration.millis(MyConst.ANIMATION_DURATION));
        pathTransition.setNode(token);
        pathTransition.setPath(path);

        GraphicsHelper.getInstance().addTransition(pathTransition);
    }

    @Override
    public void die(GameCell place) {
        final BoardCell cell = gamePanel.getBoardCell(place);
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(MyConst.ANIMATION_DURATION));
        transition.setNode(token);
        transition.setFromValue(100);
        transition.setToValue(0);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GraphicsHelper.getInstance().remove(token);
            }
        });
        GraphicsHelper.getInstance().addTransition(transition);
    }

    @Override
    public void setPlayer(Player player) {
        final Color color = player.getColor();
        String hex = "#" + Integer.toHexString(color.hashCode());
        token.setStyle("-fx-background-color: " + hex);
    }

    @Override
    public void setReady(boolean isReady) {
        if (isReady) {
            token.getStyleClass().add("ready");
        } else {
            token.getStyleClass().remove("ready");
        }
    }

    @Override
    public void create(GameCell gameCell) {
        final BoardCell cell = gamePanel.getBoardCell(gameCell);
        final Bounds b = cell.getBoundsInParent();
        final double w = (b.getWidth() - token.getPrefWidth()) / 2;
        final double h = (b.getHeight() - token.getPrefHeight()) / 2;
        token.setTranslateX(b.getMinX() + w);
        token.setTranslateY(b.getMinY() + h);
        GraphicsHelper.getInstance().add(token);
    }

    @Override
    public void setSelected(boolean b) {
        if (b) {
            token.getStyleClass().add("selected");
        } else {
            token.getStyleClass().remove("selected");
        }
    }

    @Override
    public void setSelectionPossibility(boolean b) {
        if (b) {
            token.getStyleClass().add("ready");
        } else {
            token.getStyleClass().remove("ready");
        }
    }
}
