package sample;


import javafx.scene.shape.Line;

public class ArrowShell extends AbstractShellVisualizer {
    @Override
    protected void configureShell(BoardCell cell, Shell shell) {
        shape = new Line(
                cell.getWidth() / 2,
                cell.getHeight() / 5,
                cell.getWidth() / 2,
                cell.getHeight() * 4 / 5
        );
        shape.setRotate(shell.getDirection().toAngle());
    }
}
