package sample;

import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class AstralArrowShell extends AbstractShellVisualizer {
    @Override
    protected void configureShell(BoardCell cell, Shell shell) {
        shape = new Line(
                cell.getHeight() / 5,
                cell.getWidth() / 2,
                cell.getHeight() * 4 / 5,
                cell.getWidth() / 2
        );
        shape.setStroke(Color.AQUAMARINE);
        shape.setEffect(new Glow(15));
    }
}
