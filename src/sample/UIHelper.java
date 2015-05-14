package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class UIHelper {
    public static Node createUnitChoosingList(UnitType[] values, final UnitType[] unitType, final Stage dialog) {
        VBox vBox = new VBox(5);
        for (final UnitType value : values) {
            if (value != null) {
                final Button button = ButtonBuilder.create().text(value.toString()).defaultButton(true).onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        unitType[0] = value;
                        dialog.close();
                    }
                }).build();
                vBox.getChildren().add(button);
            }
        }
        return vBox;
    }

    public static void fixSize(Button button, int size) {
        button.maxHeightProperty().setValue(size);
        button.prefHeightProperty().setValue(size);
        button.minHeightProperty().setValue(size);

        button.prefWidthProperty().setValue(size);
        button.prefWidthProperty().setValue(size);
        button.prefWidthProperty().setValue(size);
    }
}
