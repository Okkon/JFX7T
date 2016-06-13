package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ���� on 22.06.2015.
 */
public class ObjectInfoPanel extends GridPane {

    public ObjectInfoPanel() {
        super();
        getStyleClass().add("unitPanel");
    }

    public void setObj(Selectable obj) {
        getChildren().clear();
        setVgap(5);
        if (obj == null) {
            setVisible(false);
            return;
        }
        setVisible(true);
//        setGridLinesVisible(true);

        if (obj instanceof GUnit) {
            GUnit unit = (GUnit) obj;

            final TextArea textArea = new TextArea();
            textArea.setWrapText(true);
            textArea.setText(unit.getDescription());
            textArea.setEditable(false);
            UIHelper.fixWidth(textArea, 80);

            ListView<GMod> list = new ListView<GMod>();
            ObservableList<GMod> items = FXCollections.observableArrayList(unit.getMods());
            list.setItems(items);
            list.setMaxHeight(75);
            list.setCellFactory(new Callback<ListView<GMod>, ListCell<GMod>>() {
                @Override
                public ListCell<GMod> call(ListView<GMod> gModListView) {
                    return new ListCell<GMod>() {
                        @Override
                        protected void updateItem(GMod gMod, boolean b) {
                            super.updateItem(gMod, b);
                            setText(gMod == null ? "" : gMod.getName());
                        }

                        @Override
                        public void updateSelected(boolean b) {
                            super.updateSelected(b);
                            if (b) {
                                textArea.setText(getItem().getDescription());
                            }
                        }
                    };
                }
            });

            HBox hBox = new HBox(10);
            final boolean belongsToActivePlayer = GameModel.MODEL.getActivePlayer().isOwnerFor(unit);
            final List<GAction> unitSkills = new ArrayList<GAction>();
            unitSkills.addAll(unit.getSkills());
            unitSkills.addAll(unit.getExtraSkills());
            for (final GAction skill : unitSkills) {
                skill.setOwner(unit);
                final Button button = new Button();
                Image img = getSkillImage(skill);
                final ImageView imageView = new ImageView(img);
                imageView.setPreserveRatio(true);
                final int buttonSize = 64;
                imageView.setFitWidth(buttonSize);
                button.setGraphic(imageView);
                UIHelper.fixSize(button, buttonSize);
                button.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                            textArea.setText(skill.getDescription());
                        }
                    }
                });
                if (belongsToActivePlayer && unit.getPlace() != null) {
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            GameModel.MODEL.setAction(skill);
                        }
                    });
                }
                hBox.getChildren().add(button);
            }

            final Image image = ImageHelper.getImage(unit);
            final ImageView imageView = new ImageView(image);
            imageView.setFitWidth(120);
            imageView.setFitHeight(180);
            imageView.setPreserveRatio(true);

            add(imageView, 0, 0, 1, 5);
            add(new Label("Name: "), 1, 0);
            add(new Label(unit.toString()), 2, 0);
            add(new Label("HP:"), 1, 1);
            add(new Label(String.format("%d/%d", unit.getHP(), unit.getMaxHP())), 2, 1);
            add(new Label("MP:"), 1, 2);
            add(new Label(String.format("%d/%d", unit.getMP(), unit.getMaxMP())), 2, 2);
            add(new Label("Damage: "), 1, 3);
            add(new Label(String.format("%d-%d", unit.getMinDamage(), unit.getMinDamage() + unit.getRandDamage())), 2, 3);
            add(new Label(), 1, 4);
            add(hBox, 0, 5, REMAINING, 1);
            add(list, 0, 6, REMAINING, 1);
            add(textArea, 0, 7, REMAINING, 1);
        }
    }
    public Image getSkillImage(GAction skill) {
        final String imagePath = String.format("file:res/img/skills/%s.jpg", skill.getClass().getSimpleName().toLowerCase());
        Image image = new Image(imagePath);
        if (image.getPixelReader() == null) {
            image = new Image("file:res/img/skills/unknown.jpg");
        }
        return image;
    }
}
