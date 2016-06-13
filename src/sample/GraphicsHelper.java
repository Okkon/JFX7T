package sample;

import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;


public class GraphicsHelper {
    private static GraphicsHelper INSTANCE;
    private Group canvas;
    private List<Transition> transitions;

    private GraphicsHelper() {
        transitions = new ArrayList<Transition>();
    }

    public static GraphicsHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GraphicsHelper();
        }
        return INSTANCE;
    }


    public void setCanvas(Group pane) {
        this.canvas = pane;
    }

    public Group getCanvas() {
        return canvas;
    }

    public void add(Node node) {
        getCanvas().getChildren().add(node);
    }

    public void remove(Node node) {
        getCanvas().getChildren().remove(node);
    }

    public GraphicsHelper addTransition(Transition transition) {
        transitions.add(transition);
        return INSTANCE;
    }

    public void play() {
        SequentialTransition transition = new SequentialTransition();
        for (Transition subTransition : transitions) {
            transition.getChildren().add(subTransition);
        }
        transition.play();
        transitions.clear();
    }
}
