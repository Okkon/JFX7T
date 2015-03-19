package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("7T");
        GameModel gameModel = GameModel.MODEL;
        gameModel.setBoard(14, 8);
        GamePanel mainPane = new GamePanel(gameModel);
        gameModel.init();

        final Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
