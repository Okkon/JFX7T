package sample;

import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;

public class GObjectVisualizerImpl implements GObjectVisualizer {
    private GamePanel gamePanel;
    private Shape token;
    private final Label hpLabel = new Label();
    private final StackPane pane = new StackPane();
    private final GObject obj;
    private Transition transition;
    private Image image;

    public GObjectVisualizerImpl(final GObject obj, GamePanel gamePanel) {
        obj.setVisualizer(this);
        this.gamePanel = gamePanel;
        this.obj = obj;
        final int size = MyConst.OBJECT_VISUALIZER_SIZE;
        pane.setFocusTraversable(false);
        hpLabel.setPrefSize(10, 10);
        if (obj instanceof GUnit) {
            GUnit unit = (GUnit) obj;
            token = new Circle(size / 2);
            token.getStyleClass().add("unit");
            image = ImageHelper.getImage(unit);
            token.setFill(new ImagePattern(image, 0, 0, 1, 1, true));
            hpLabel.setText(String.valueOf(unit.getHP()));
            for (GMod mod : unit.getMods()) {
                mod.applyEffect(unit);
            }
        }
        if (obj instanceof Tower) {
            Tower tower = (Tower) obj;
            token = new Rectangle(size, size);
            Image img = ImageHelper.getImage(tower, tower instanceof MainTower);
            token.setFill(new ImagePattern(img, 0, 0, 1, 1, true));
            token.getStyleClass().add("tower");
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
        pathTransition.setDuration(MyConst.MOVE_ANIMATION_DURATION);
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
        token.setStroke(color);
//        StrokeTransition transition = StrokeTransitionBuilder.create().shape(token).toValue(color).duration(MyConst.ANIMATION_DURATION.multiply(2)).build();
//        GraphicsHelper.getInstance().addTransition(transition);
        /*String hex = "#" + Integer.toHexString(color.hashCode());
        token.setStyle("-fx-stroke: " + hex);*/
    }

    @Override
    public void setReady(boolean isReady) {
        final ObservableList<String> styleClass = token.getStyleClass();
        styleClass.remove("ready");
        if (isReady) {
            styleClass.add("ready");
        }
    }

    @Override
    public void create(GameCell gameCell) {
        final BoardCell cell = gamePanel.getBoardCell(gameCell);
        final Bounds b = cell.getBoundsInParent();
        final double w = (b.getWidth() - MyConst.OBJECT_VISUALIZER_SIZE) / 2;
        final double h = (b.getHeight() - MyConst.OBJECT_VISUALIZER_SIZE) / 2;
        pane.setTranslateX(b.getMinX() + w);
        pane.setTranslateY(b.getMinY() + h);
        pane.getChildren().addAll(token, hpLabel);
        hpLabel.setTranslateX(25);
        hpLabel.setTranslateY(25);
        setPlayer(obj.getPlayer());
        GraphicsHelper.getInstance().add(pane);
        if (GameModel.MODEL.getPhase() != null) {
            ScaleTransition transition = new ScaleTransition();
            transition.setNode(pane);
            transition.setDuration(MyConst.ANIMATION_DURATION.divide(1d));
            final double sizeChange = 1d;
            transition.setFromX(0);
            transition.setFromY(0);
            transition.setToX(sizeChange);
            transition.setToY(sizeChange);
            GraphicsHelper.getInstance().addTransition(transition);
        }
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
        GObjectVisualizerImpl visualizer = (GObjectVisualizerImpl) aim.getVisualizer();
        XY c = UIHelper.getCenter(this);
        XY c2 = UIHelper.getCenter(visualizer);

        final Shape sword = createWeapon();

        GraphicsHelper.getInstance().add(sword);
        Path path = PathBuilder.create()
                .elements(
                        new MoveTo(c.getX(), c.getY()),
                        new LineTo(c2.getX(), c2.getY()))
                .build();

        PathTransition pathTransition = PathTransitionBuilder.create()
                .node(sword)
                .duration(MyConst.MOVE_ANIMATION_DURATION)
                .path(path)
                .autoReverse(true)
                .cycleCount(2)
                .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                .onFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        GraphicsHelper.getInstance().remove(sword);
                    }
                }).build();
        GraphicsHelper.getInstance().addTransition(pathTransition);
    }

    private Shape createWeapon() {
        double h = pane.getHeight();
        double w = pane.getWidth();
        final Shape sword = new Polyline(
                w * 1 / 5,
                h * 1 / 2,
                w * 4 / 5,
                h * 1 / 2,
                w * 2 / 5,
                h * 1 / 2,
                w * 2 / 5,
                h * 3 / 5,
                w * 2 / 5,
                h * 2 / 5
        );
//        sword.setStroke(Color.BLANCHEDALMOND);
        sword.setStrokeWidth(3);
        return sword;
    }

    @Override
    public void applyEffect(String effect) {
        token.getStyleClass().remove(effect);
        token.getStyleClass().add(effect);
    }

    @Override
    public void setPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void changeOwner(Player newOwner) {
        ScaleTransition transition = new ScaleTransition();
        transition.setNode(token);
        transition.setDuration(MyConst.ANIMATION_DURATION.divide(3d));
        final double sizeChange = 2d;
        transition.setToX(sizeChange);
        transition.setToY(sizeChange);
        StrokeTransition strokeTransition = new StrokeTransition(
                MyConst.ANIMATION_DURATION.divide(3d),
                this.token
                );
        strokeTransition.setToValue(newOwner.getColor());
        ScaleTransition transition2 = new ScaleTransition();
        transition2.setNode(token);
        transition2.setDuration(MyConst.ANIMATION_DURATION.divide(3d));
        transition2.setToX(1d);
        transition2.setToY(1d);
        GraphicsHelper.getInstance().addTransition(transition);
        GraphicsHelper.getInstance().addTransition(strokeTransition);
        GraphicsHelper.getInstance().addTransition(transition2);
    }

    @Override
    public void setSelectionPossibility(boolean b) {
        final BoardCell boardCell = obj.getPlace().getVisualizer();
        boardCell.setSelectionPossibility(b);

        /*if (transition != null) {
            transition.stop();
            setPlayer(obj.getPlayer());
        }
        if (b) {
            final Color color = obj.getPlayer().getColor();
            transition = new StrokeTransition(MyConst.ANIMATION_DURATION.divide(2), token, color.darker().darker(), color.brighter().brighter());
            transition.setCycleCount(Animation.INDEFINITE);
            transition.setAutoReverse(true);
            transition.play();
        }*/
    }

    public StackPane getPane() {
        return pane;
    }
}
