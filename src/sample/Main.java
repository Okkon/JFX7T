package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Core.GameModel;
import sample.Core.Scenario.Scenario1;
import sample.Graphics.GamePanel;
import sample.Graphics.GraphicsHelper;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("7T");
        GameModel gameModel = GameModel.MODEL;
        gameModel.init();
        final GamePanel mainPane = new GamePanel(gameModel);

        Group canvas = new Group();
        GraphicsHelper.getInstance().setCanvas(canvas);
        canvas.getChildren().add(mainPane);
        final Scene scene = new Scene(canvas);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        gameModel.startScenario(new Scenario1());

    }

    public static void main(String[] args) {
        launch(args);
    }
}
