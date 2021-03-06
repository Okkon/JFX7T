package sample.Graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Core.ObjectType;
import sample.XY;


public class UIHelper {
    public static Node createUnitChoosingList(ObjectType[] values, final ObjectType[] objectType, final Stage dialog) {
        VBox vBox = new VBox(5);
        for (final ObjectType value : values) {
            if (value != null) {
                final Button button = ButtonBuilder.create().text(value.toString()).defaultButton(true).onAction((EventHandler<ActionEvent>) actionEvent -> {
                    objectType[0] = value;
                    dialog.close();
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

        node.maxWidthProperty().setValue(size);
        node.prefWidthProperty().setValue(size);
        node.minWidthProperty().setValue(size);
    }

    public static void fixHeight(Control control, int height) {
        control.minHeightProperty().setValue(height);
        control.prefHeightProperty().setValue(height);
        control.maxHeightProperty().setValue(height);
    }

    public static XY getCenter(GObjectVisualizerImpl impl) {
        return getCenter(impl.getPane().getBoundsInParent());
    }

    public static XY getCenter(Bounds bounds) {
        return XY.get(
                new Double(bounds.getMinX() + bounds.getWidth() / 2).intValue(),
                new Double(bounds.getMinY() + bounds.getHeight() / 2).intValue());
    }
}
