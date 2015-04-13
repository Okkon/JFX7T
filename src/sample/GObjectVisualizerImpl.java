package sample;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class GObjectVisualizerImpl implements GObjectVisualizer {
    private final GamePanel gamePanel;
    private final Label token = new Label();
    private final Label hpLabel = new Label();
    private final StackPane pane = new StackPane();
    private final GObject obj;

    public GObjectVisualizerImpl(final GObject obj, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.obj = obj;
        final int size = MyConst.OBJECT_VISUALIZER_SIZE;
        token.setPrefSize(size, size);
        hpLabel.setPrefSize(10, 10);
        if (obj instanceof GUnit) {
            GUnit unit = (GUnit) obj;
            token.getStyleClass().add("unit");
            token.setText(unit.getName().substring(0, 2));
            hpLabel.setText(String.valueOf(unit.getHP()));
        }
        if (obj instanceof Tower) {
            Tower tower = (Tower) obj;
            token.getStyleClass().add("tower");
            token.setText("T");
        }
        setReady(obj.canAct());
        token.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameModel.MODEL.press(obj);
            }
        });
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
        pathTransition.setDuration(MyConst.ANIMATION_DURATION);
        pathTransition.setNode(pane);
        pathTransition.setPath(path);

        GraphicsHelper.getInstance().addTransition(pathTransition);
    }

    @Override
    public void die(GameCell place) {
        final BoardCell cell = gamePanel.getBoardCell(place);
        FadeTransition transition = new FadeTransition();
        transition.setDuration(MyConst.ANIMATION_DURATION);
        transition.setNode(pane);
        transition.setFromValue(100);
        transition.setToValue(0);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GraphicsHelper.getInstance().remove(pane);
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
        /*token.setTranslateX(b.getMinX() + w);
        token.setTranslateY(b.getMinY() + h);
        hpLabel.setTranslateX(b.getMaxX() - 7);
        hpLabel.setTranslateY(b.getMaxY() - 15);*/
        pane.setTranslateX(b.getMinX() + w);
        pane.setTranslateY(b.getMinY() + h);
        pane.getChildren().addAll(token, hpLabel);
        hpLabel.setTranslateX(25);
        hpLabel.setTranslateY(25);
        GraphicsHelper.getInstance().add(pane);
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
