package sample.Shells;

import javafx.scene.paint.Color;
import sample.Core.Shell;
import sample.Graphics.BoardCell;

public class AstralArrowShell extends ArrowShell {
    @Override
    protected void configureShell(BoardCell cell, Shell shell) {
        super.configureShell(cell, shell);
        shape.setStroke(Color.BLUEVIOLET);
    }
}
