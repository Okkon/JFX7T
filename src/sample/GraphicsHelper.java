package sample;

import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.util.List;


public class GraphicsHelper {
    private static GraphicsHelper INSTANCE;
    private Pane pane;
    private List<Transition> transitions;

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

    public void addTransition(Transition transition) {
        transitions.add(transition);
    }

    public void play() {
        SequentialTransition transition = new SequentialTransition();
        for (Transition subTransition : transitions) {
            transition.getChildren().add(subTransition);
        }
        transition.play();
    }
}
