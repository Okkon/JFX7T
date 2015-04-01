package sample;

import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;


public class FireballShell extends AbstractShellVisualizer {
    @Override
    public void create(GameCell cell, Shell shell) {
        super.create(cell, shell);
        shape.setFill(Color.PERU);
        shape.setEffect(new Glow(15));
    }
}
