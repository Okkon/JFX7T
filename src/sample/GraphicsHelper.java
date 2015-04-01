package sample;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;


public class GraphicsHelper {
    private static GraphicsHelper INSTANCE;
    private Pane pane;

    private GraphicsHelper() {

    }

    public static GraphicsHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GraphicsHelper();
        }
        return INSTANCE;
    }


    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Pane getPane() {
        return pane;
    }

    public void add(Node node) {
        getPane().getChildren().add(node);
    }

    public void remove(Shape shape) {
        getPane().getChildren().remove(shape);
    }
}
