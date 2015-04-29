package sample.Shells;


import javafx.scene.shape.Line;
import sample.AbstractShellVisualizer;
import sample.BoardCell;
import sample.Shell;

public class ArrowShell extends AbstractShellVisualizer {
    @Override
    protected void configureShell(BoardCell cell, Shell shell) {
        shape = new Line(
                cell.getHeight() / 5,
                cell.getWidth() / 2,
                cell.getHeight() * 4 / 5,
                cell.getWidth() / 2
        );
    }
}
