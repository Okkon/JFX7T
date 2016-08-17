package sample.Shells;

import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import sample.Core.Shell;
import sample.Graphics.AbstractShellVisualizer;
import sample.Graphics.BoardCell;


public class FireballShell extends AbstractShellVisualizer {
    @Override
    protected void configureShell(BoardCell cell, Shell shell) {
        super.configureShell(cell, shell);
        shape.setFill(Color.PERU);
        shape.setEffect(new Glow(15));
    }
}
