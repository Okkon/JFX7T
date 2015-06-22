package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;


public class UnitSelectorImpl implements UnitSelector {
    private final ObjectInfoPanel objectInfoPanel;
    private GUnit selectedUnit;
    private Stage dialog;

    public UnitSelectorImpl(List<GUnit> units, Stage dialog) {
        objectInfoPanel = new ObjectInfoPanel();
        this.dialog = dialog;
        ListView<GUnit> list = new ListView<GUnit>();
        ObservableList<GUnit> items = FXCollections.observableArrayList(units);
        list.setItems(items);
        list.setPrefWidth(100);
        list.setPrefHeight(70);
        list.getSelectionModel().select(0);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GUnit>() {
            @Override
            public void changed(ObservableValue<? extends GUnit> observableValue, GUnit oldVal, GUnit newVal) {
                selectedUnit = newVal;
//                objectInfoPanel.setObj(selectedUnit);
            }
        });

        dialog.setScene(
                new Scene(
                        HBoxBuilder.create().styleClass("modal-dialog").children(
                                list/*,
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
