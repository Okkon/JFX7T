package sample.Shells;


import javafx.scene.shape.Polyline;
import sample.AbstractShellVisualizer;
import sample.BoardCell;
import sample.Shell;

public class ArrowShell extends AbstractShellVisualizer {
    @Override
    protected void configureShell(BoardCell cell, Shell shell) {
        shape = new Polyline(
                cell.getHeight() * 3 / 5,
                cell.getWidth() / 6 * 2,

                cell.getHeight() * 4 / 5,
                cell.getWidth() / 2,

                cell.getHeight() * 3 / 5,
                cell.getWidth() / 6 * 4,

                cell.getHeight() * 4 / 5,
                cell.getWidth() / 2,

                cell.getHeight() / 5,
                cell.getWidth() / 2
        );
    }
}
