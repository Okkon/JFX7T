package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
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

        gameModel.locateUnits();
        gameModel.startHour();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
