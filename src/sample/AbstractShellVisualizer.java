package sample;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.shape.*;
import javafx.util.Duration;


public abstract class AbstractShellVisualizer implements ShellVisualizer {
    protected Shape shape;
    protected Shell shell;

    @Override
    public void step(GameCell cell, final GameCell nextCell) {
        final BoardCell fromCell = (BoardCell) cell.getVisualizer();
        final BoardCell toCell = (BoardCell) nextCell.getVisualizer();
        final Bounds bounds = fromCell.getBoundsInParent();
        final Bounds bounds2 = toCell.getBoundsInParent();

        Path path = new Path();
        path.getElements().add(new MoveTo(bounds.getMinX() + bounds.getWidth() / 2, bounds.getMinY() + bounds.getHeight() / 2));
        path.getElements().add(new LineTo(bounds2.getMinX() + bounds2.getWidth() / 2, bounds2.getMinY() + bounds2.getHeight() / 2));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setNode(shape);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);

        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setAutoReverse(true);

        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (shell.stopped) {
                    destroy(nextCell);
                }
            }
        });
        GraphicsHelper.getInstance().addTransition(pathTransition);
    }

    @Override
    public void create(GameCell cell, Shell shell) {
        this.shell = shell;
        final BoardCell fromCell = (BoardCell) cell.getVisualizer();
        shape = new Circle(
                fromCell.getWidth() / 2,
                fromCell.getHeight() / 2,
                Math.max(fromCell.getWidth(), fromCell.getHeight()) / 3);
        GraphicsHelper.getInstance().add(shape);
    }

    @Override
    public void destroy(GameCell cell) {
        FadeTransition transition = new FadeTransition();
        transition.setNode(shape);
        transition.setDuration(Duration.millis(1000));
        transition.setFromValue(100);
        transition.setToValue(0);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GraphicsHelper.getInstance().remove(shape);
            }
        });
    }
}
