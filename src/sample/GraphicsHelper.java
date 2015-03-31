package sample;

import javafx.scene.canvas.Canvas;

/**
 * Created by Олег on 31.03.2015.
 */
public class GraphicsHelper {
    private static GraphicsHelper INSTANCE;
    private Canvas canvas;

    private GraphicsHelper() {

    }

    public static GraphicsHelper getInstance() {
        return INSTANCE == null ? new GraphicsHelper() : INSTANCE;
    }


    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
