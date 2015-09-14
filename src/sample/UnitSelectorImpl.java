package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;


public class UnitSelectorImpl implements UnitSelector {
    private final ObjectInfoPanel objectInfoPanel;
    private GUnit selectedUnit;
    private Stage dialog;

    private class UnitCell extends ListCell<GUnit> {
        @Override
        protected void updateItem(GUnit gUnit, boolean b) {
            super.updateItem(gUnit, b);
            if (gUnit == null) {
                return;
            }
            final Image image = ImageHelper.getImage(gUnit);
            final ImageView imageView = new ImageView(image);
            imageView.setFitWidth(64);
            imageView.setFitHeight(64);
            imageView.setPreserveRatio(true);
            setGraphic(imageView);
        }
    }

    public UnitSelectorImpl(final List<GUnit> units, Stage dialog) {
        objectInfoPanel = new ObjectInfoPanel();
        this.dialog = dialog;

        /*ListView<GUnit> list = new ListView<GUnit>();
        ObservableList<GUnit> items = FXCollections.observableArrayList(units);
        list.setItems(items);
        list.setPrefWidth(400);
        list.setPrefHeight(200);
        list.getSelectionModel().select(0);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        list.setCellFactory(new Callback<ListView<GUnit>, ListCell<GUnit>>() {
            @Override
            public ListCell<GUnit> call(ListView<GUnit> gUnitListView) {
                return new UnitCell();
            }
        });

        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GUnit>() {
            @Override
            public void changed(ObservableValue<? extends GUnit> observableValue, GUnit oldVal, GUnit newVal) {
                selectedUnit = newVal;
            }
        });*/

        GridPane pane = new GridPane();
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
                }
            });

            final int unitPaneWidth = 3;
            pane.add(imageView, i % unitPaneWidth, i / unitPaneWidth, 1, 1);
        }

        dialog.setScene(
                new Scene(
                        HBoxBuilder.create().styleClass("modal-dialog").children(
                                pane/*,
                                objectInfoPanel*/
                        ).build(),
                        Color.GRAY
                )
        );
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
}
