package sample;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
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
        final Bounds bounds1 = getBounds(currentCell);
        final Bounds bounds2 = getBounds(cellToGo);

        Path path = new Path();
        final double w = bounds1.getWidth() / 2;
        final double h = bounds1.getHeight() / 2;
        path.getElements().add(new MoveTo(bounds1.getMinX() + w, bounds1.getMinY() + h));
        path.getElements().add(new LineTo(bounds2.getMinX() + w, bounds2.getMinY() + h));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(MyConst.ANIMATION_DURATION);
        pathTransition.setNode(pane);
        pathTransition.setPath(path);

        GraphicsHelper.getInstance().addTransition(pathTransition);
    }

    protected Bounds getBounds(GameCell cell) {
        final BoardCell fromCell = (BoardCell) cell.getVisualizer();
        return fromCell.getBoundsInParent();
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
        pane.setTranslateX(b.getMinX() + w);
        pane.setTranslateY(b.getMinY() + h);
        pane.getChildren().addAll(token, hpLabel);
        hpLabel.setTranslateX(25);
        hpLabel.setTranslateY(25);
        setPlayer(obj.getPlayer());
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
    public void changeHP(final int hp) {
        ScaleTransition transition = new ScaleTransition();
        transition.setNode(hpLabel);
        transition.setDuration(MyConst.ANIMATION_DURATION.divide(3d));
        final double sizeChange = 2d;
        transition.setToX(sizeChange);
        transition.setToY(sizeChange);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                hpLabel.setText(String.valueOf(hp));
            }
        });
        ScaleTransition transition2 = new ScaleTransition();
        transition2.setNode(hpLabel);
        transition2.setDuration(MyConst.ANIMATION_DURATION.divide(3d));
        transition2.setToX(1d);
        transition2.setToY(1d);
        GraphicsHelper.getInstance().addTransition(transition);
        GraphicsHelper.getInstance().addTransition(new PauseTransition(MyConst.ANIMATION_DURATION.divide(3d)));
        GraphicsHelper.getInstance().addTransition(transition2);
    }

    @Override
    public void startAttack(GObject aim) {

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
