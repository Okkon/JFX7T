package sample.Graphics;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.geometry.Bounds;
import javafx.scene.shape.*;
import sample.Core.GameCell;
import sample.Core.Shell;
import sample.Core.ShellVisualizer;
import sample.MyConst;


public abstract class AbstractShellVisualizer implements ShellVisualizer {
    protected Shape shape;
    protected Shell shell;

    @Override
    public void step(GameCell cell, final GameCell nextCell) {
        final BoardCell fromCell = cell.getVisualizer();
        final BoardCell toCell = nextCell.getVisualizer();
        final Bounds bounds = fromCell.getBoundsInParent();
        final Bounds bounds2 = toCell.getBoundsInParent();

        Path path = new Path();
        path.getElements().add(new MoveTo(bounds.getMinX() + bounds.getWidth() / 2, bounds.getMinY() + bounds.getHeight() / 2));
        path.getElements().add(new LineTo(bounds2.getMinX() + bounds2.getWidth() / 2, bounds2.getMinY() + bounds2.getHeight() / 2));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(MyConst.SHOT_ANIMATION_DURATION);
        pathTransition.setNode(shape);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);

        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setAutoReverse(true);

        GraphicsHelper.getInstance().addTransition(pathTransition);
        /*TranslateTransition transition = new TranslateTransition(Duration.millis(MyConst.ANIMATION_DURATION), shape);
        transition.setToX(bounds2.getMinX() + bounds2.getWidth() / 2);
        transition.setToY(bounds2.getMinY() + bounds2.getHeight() / 2);
        GraphicsHelper.getInstance().addTransition(transition);*/
    }

    @Override
    public void create(GameCell cell, Shell shell) {
        this.shell = shell;
        final BoardCell fromCell = cell.getVisualizer();
        configureShell(fromCell, shell);
        GraphicsHelper.getInstance().add(shape);
    }

    protected void configureShell(BoardCell cell, Shell shell) {
        shape = new Circle(
                cell.getWidth() / 2,
                cell.getHeight() / 2,
                Math.max(cell.getWidth(), cell.getHeight()) / 3);
    }

    @Override
    public void destroy(GameCell cell) {
        FadeTransition transition = new FadeTransition();
        transition.setNode(shape);
        transition.setDuration(MyConst.ANIMATION_DURATION);
        transition.setFromValue(100);
        transition.setToValue(0);
        transition.setOnFinished(actionEvent -> GraphicsHelper.getInstance().remove(shape));
        GraphicsHelper.getInstance().addTransition(transition);
    }
}
