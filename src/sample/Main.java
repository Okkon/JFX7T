package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("7T");
        GameModel gameModel = GameModel.MODEL;
        gameModel.setBoard(14, 8);
        final GamePanel mainPane = new GamePanel(gameModel);
        gameModel.init();
        Pane pane = new Pane();
        Pane effectsPane = new Pane();
        GraphicsHelper.getInstance().setPane(effectsPane);
        pane.getChildren().addAll(mainPane, effectsPane);

        final Scene scene = new Scene(pane);


        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        gameModel.locateUnits();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
