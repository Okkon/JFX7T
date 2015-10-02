package sample;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;


public class UnitSelectorImpl implements UnitSelector {
    private final ObjectInfoPanel infoPanel;
    private GUnit selectedUnit;
    private Stage dialog;

    public UnitSelectorImpl(final List<GUnit> units, final Stage dialog) {
        this.dialog = dialog;

        GridPane pane = new GridPane();
        pane.setGridLinesVisible(true);
        pane.setVgap(5);
        pane.setHgap(5);
        pane.setPadding(new Insets(5, 10, 5, 10));
        pane.setStyle("-fx-background-color: ivory ;");
        final int unitPaneWidth = 3;
        infoPanel = new ObjectInfoPanel();
        for (int i = 0; i < units.size(); i++) {
            final GUnit unit = units.get(i);
            final Image image = ImageHelper.getImage(unit);
            final ImageView imageView = new ImageView(image);
            imageView.setFitWidth(64);
            imageView.setFitHeight(64);
            imageView.setPreserveRatio(true);
            imageView.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedUnit = unit;
                    infoPanel.setObj(unit);

                }
            });
            HBox box = new HBox();
            box.getChildren().add(imageView);
            box.getStyleClass().add("unitSelector");

            pane.add(box, i % unitPaneWidth, i / unitPaneWidth, 1, 1);
        }
        infoPanel.setObj(units.get(0));
        pane.add(infoPanel, unitPaneWidth, 0, GridPane.REMAINING, GridPane.REMAINING);
        final Label label = new Label();
        label.textProperty().setValue("123");
        pane.add(label, 0, units.size() / unitPaneWidth + 1, unitPaneWidth, GridPane.REMAINING);

        final Scene scene = new Scene(
                HBoxBuilder.create().styleClass("modal-dialog").children(
                        pane
                ).build(),
                Color.GRAY
        );
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        dialog.setScene(scene);
        dialog.show();
    }

    @Override
    public GUnit getSelectedUnit() {
        return selectedUnit;
    }

    @Override
    public void close() {
        dialog.close();
    }

    @Override
    public void setUnitCounter(int unitCounter) {
        dialog.setTitle("Units left: " + unitCounter);
    }
}
