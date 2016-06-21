package sample.Panels;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Player;

import java.io.File;

public class PlayerInfoPanel extends GridPane {

    public PlayerInfoPanel() {
        super();
        getStyleClass().add("playerPanel");
    }

    public void setPlayer(final Player player) {
        getChildren().clear();
        setVgap(5);
        if (player == null) {
            setVisible(false);
            return;
        }
        setVisible(true);
        setStyle("-fx-border-color: #" + player.getColor().toString().substring(2));

        //   Player Image
        final Image image = player.getImage();
        final ImageView imageView = new ImageView(image);
        imageView.setFitWidth(120);
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(true);

        add(imageView, 0, 0, 1, 5);

        Label label = new Label("Name: ");
        label.setTextFill(player.getColor());
        add(label, 1, 0);
        add(new Label(player.getName()), 2, 0);
        add(new Label("Score:"), 1, 1);
        add(new Label(String.valueOf(player.getScore())), 2, 1);
        add(new Label("Units:"), 1, 2);
        add(new Label(String.format("%s/%s", player.getActiveUnits().size(), player.getUnits().size())), 2, 2);
        Button button = new Button();
        button.setText("Select Image");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setTitle("Select Player Image");
                File file = fileChooser.showOpenDialog(new Stage());
                player.setImage(new Image(file.toURI().toString()));
                setPlayer(player);
            }
        });
        add(button, 1, 3, 2, 1);

    }
}
