package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Control;
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

    public static void fixSize(Control node, int size) {
        node.maxHeightProperty().setValue(size);
        node.prefHeightProperty().setValue(size);
        node.minHeightProperty().setValue(size);

        node.prefWidthProperty().setValue(size);
        node.prefWidthProperty().setValue(size);
        node.prefWidthProperty().setValue(size);
    }

    public static void fixWidth(Control control, int width) {
        control.prefWidthProperty().setValue(width);
        control.prefWidthProperty().setValue(width);
        control.prefWidthProperty().setValue(width);
    }
}
