package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
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

        node.maxWidthProperty().setValue(size);
        node.prefWidthProperty().setValue(size);
        node.minWidthProperty().setValue(size);
    }

    public static void fixWidth(Control control, int width) {
        control.prefWidthProperty().setValue(width);
    }

    public static XY getCenter(GObjectVisualizerImpl impl) {
        return getCenter(impl.getPane().getBoundsInParent());
    }

    public static XY getCenter(Bounds bounds) {
        return new XY(
                new Double(bounds.getMinX() + bounds.getWidth() / 2).intValue(),
                new Double(bounds.getMinY() + bounds.getHeight() / 2).intValue());
    }
}
