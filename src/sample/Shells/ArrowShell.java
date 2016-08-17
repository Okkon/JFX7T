package sample.Shells;

import javafx.scene.shape.Polyline;
import sample.Core.Shell;
import sample.Graphics.AbstractShellVisualizer;
import sample.Graphics.BoardCell;

public class ArrowShell extends AbstractShellVisualizer {
    @Override
    protected void configureShell(BoardCell cell, Shell shell) {
        final double arrow_length = 0.75;
        final double sharp_length = 0.2;
        final double sharp_width = 0.05;
        shape = new Polyline(
                cell.getWidth() * (arrow_length - sharp_length),
                cell.getHeight() * (0.5 + sharp_width),

                cell.getWidth() * arrow_length,
                cell.getHeight() / 2,

                cell.getWidth() * (arrow_length - sharp_length),
                cell.getHeight() * (0.5 - sharp_width),

                cell.getWidth() * arrow_length,
                cell.getHeight() / 2,

                cell.getWidth() * (1 - arrow_length),
                cell.getHeight() / 2
        );
    }
}
