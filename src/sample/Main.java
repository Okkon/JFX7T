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
        gameModel.setBoard(14, 8);
        final GamePanel mainPane = new GamePanel(gameModel);
        gameModel.init();
        Group canvas = new Group();
        GraphicsHelper.getInstance().setCanvas(canvas);
        canvas.getChildren().add(mainPane);
        final Scene scene = new Scene(canvas);
        /*Line redLine = LineBuilder.create()
                .startX(296)
                .startY(128)
                .endX(401)
                .endY(233)
                *//*.fill(Color.RED)
                .stroke(Color.RED)*//*
                .strokeWidth(10.0f)
                .build();

        StrokeTransition ft = new StrokeTransition(Duration.millis(3000), redLine, Color.RED, Color.BLUE);
        ft.setCycleCount(4);
        ft.setAutoReverse(true);
        ft.play();
        mainPane.getChildren().add(redLine);

        final Circle circle = new Circle(30,30,15);
        mainPane.getChildren().add(circle);
        circle.setFill(Color.PERU);
        circle.setEffect(new Glow(15));

        Path path = new Path();
        path.getElements().add (new MoveTo(50f, 50f));
        path.getElements().add (new CubicCurveTo (10, 10, 400, 10, 400, 400));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(10000));
        pathTransition.setNode(circle);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainPane.getChildren().remove(circle);
            }
        });

        pathTransition.play();*/

        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        gameModel.locateUnits();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
