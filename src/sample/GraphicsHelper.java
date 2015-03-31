package sample;

import javafx.scene.Node;
import javafx.scene.layout.Pane;


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

    public static void add(Node node) {
        INSTANCE.getPane().getChildren().add(node);
    }
}
