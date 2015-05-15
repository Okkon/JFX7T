package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePanel extends GridPane implements MainVisualizer {
    private GameModel model;
    private Map<GameCell, BoardCell> cells;
    private VBox actionPanel;
    private GridPane gameInfoPanel;
    private BorderPane controlPanel;
    private GridPane boardPane;
    private final TextArea gameLog;
    private Label selectedObjLabel = new Label();
    private Label selectedActionLabel = new Label();
    private Label selectedPlayer = new Label();
    private Label turnLabel = new Label();
    private GridPane objInfoPanel = new GridPane();

    public GamePanel(GameModel gameModel) {
        gameModel.setGraphics(this);
        this.model = gameModel;
        initComponents();
        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                    GameModel.MODEL.cancel();
                }
            }
        });

        gameLog = new TextArea();
        add(boardPane, 0, 0);
        add(controlPanel, 1, 0, 1, REMAINING);
        add(gameLog, 0, 1, 1, 1);
    }

    private void initComponents() {
        initBoard();
        initControlPanel();
    }

    private void initControlPanel() {
        gameInfoPanel = new GridPane();
        gameInfoPanel.setPrefWidth(300);
        gameInfoPanel.add(new Label("Selected Obj - "), 0, 0);
        gameInfoPanel.add(selectedObjLabel, 1, 0);
        gameInfoPanel.add(new Label("Selected Action - "), 0, 1);
        gameInfoPanel.add(selectedActionLabel, 1, 1);
        gameInfoPanel.add(new Label("Selected Player - "), 0, 2);
        gameInfoPanel.add(selectedPlayer, 1, 2);
        gameInfoPanel.add(new Label("Turn - "), 0, 3);
        gameInfoPanel.add(turnLabel, 1, 3);

        objInfoPanel.getStyleClass().add("unitPanel");

        actionPanel = new VBox(5);
        for (GAction action : GameModel.MODEL.getActions()) {
            addAction(action);
        }

        controlPanel = new BorderPane();
        controlPanel.getStyleClass().add("control-panel");
        controlPanel.setTop(gameInfoPanel);
        controlPanel.setCenter(objInfoPanel);
        controlPanel.setBottom(actionPanel);
    }

    private void initBoard() {
        boardPane = new GridPane();
        boardPane.setGridLinesVisible(true);
        cells = new HashMap<GameCell, BoardCell>();
        final Map<XY, GameCell> board = model.getBoard();
        final EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                BoardCell selectedCell = (BoardCell) mouseEvent.getSource();
                model.press(selectedCell.getGameCell());
                refresh();
            }
        };
        for (Map.Entry<XY, GameCell> entry : board.entrySet()) {
            final BoardCell boardCell = new BoardCell(entry.getValue());
            final int cellSize = MyConst.CELL_SIZE;
            boardCell.setMinSize(cellSize, cellSize);
            boardCell.setPrefSize(cellSize, cellSize);
            boardCell.setMaxSize(cellSize, cellSize);
            boardCell.setOnMousePressed(handler);
            boardPane.add(boardCell, entry.getKey().getX(), entry.getKey().getY());
            cells.put(entry.getValue(), boardCell);
        }
    }

    private void addAction(final GAction action) {
        final Button button = new Button(action.getName());
        actionPanel.getChildren().add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GameModel.MODEL.setAction(action);
            }
        });
    }

    @Override
    public void refresh() {
//                cell.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/resources/pic.png"))));
    }

    @Override
    public GObject createUnitCreationPanel() {
        final UnitType[] unitType = new UnitType[1];
        ListView<Player> list = new ListView<Player>();
        ObservableList<Player> items = FXCollections.observableArrayList(model.getPlayers());
        list.setItems(items);
        list.setPrefWidth(100);
        list.setPrefHeight(70);
        list.getSelectionModel().select(0);

        final Stage dialog = createDialog();
        dialog.setScene(
                new Scene(
                        VBoxBuilder.create().styleClass("modal-dialog").children(
                                list,
                                UIHelper.createUnitChoosingList(UnitType.values(), unitType, dialog)
                        ).build(),
                        Color.GRAY
                )
        );
        dialog.showAndWait();
        final GObject gObject = GObjectFactory.create(unitType[0]);
        gObject.setPlayer(list.getSelectionModel().getSelectedItem());
        return gObject;
    }

    @Override
    public void showObjInfo(Selectable obj) {
        if (obj instanceof GUnit) {
            GUnit gUnit = (GUnit) obj;
            createUnitInfoPanel(objInfoPanel, gUnit);
        } else {
            createUnitInfoPanel(objInfoPanel, null);
        }

    }

    private void createUnitInfoPanel(GridPane pane, GUnit unit) {
        pane.getChildren().clear();
        pane.setVgap(5);
        if (unit == null) {
            return;
        }

        final TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setText(unit.getDescription());
        textArea.setEditable(false);

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
        for (final GAction skill : unit.getSkills()) {
            skill.setOwner(unit);
            final Button button = new Button();
            final String imagePath = String.format("file:res/img/skills/%s.jpg", skill.getClass().getSimpleName().toLowerCase());
            Image img = new Image(imagePath);
            final ImageView imageView = new ImageView(img);
            imageView.setPreserveRatio(true);
            final int buttonSize = 64;
            imageView.setFitWidth(buttonSize);
            button.setGraphic(imageView);
            UIHelper.fixSize(button, buttonSize);
//            button.setText(skill.getName());
            button.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                        textArea.setText(skill.getDescription());
                    }
                }
            });
            if (belongsToActivePlayer) {
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        GameModel.MODEL.setAction(skill);
                    }
                });
            }
            hBox.getChildren().add(button);
        }

        final Image image = unit.getVisualizer().getImage();
        final ImageView imageView = new ImageView(image);
        imageView.setFitWidth(120);
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(true);

        pane.add(imageView, 0, 0, 1, 4);
        pane.add(new Label("Name: "), 1, 0);
        pane.add(new Label(unit.toString()), 2, 0);
        pane.add(new Label("HP:"), 1, 1);
        pane.add(new Label(String.format("%d/%d", unit.getHP(), unit.getMaxHP())), 2, 1);
        pane.add(new Label("MP:"), 1, 2);
        pane.add(new Label(String.format("%d/%d", unit.getMP(), unit.getMaxMP())), 2, 2);
        pane.add(new Label("Damage: "), 1, 3);
        pane.add(new Label(String.format("%d-%d", unit.getMinDamage(), unit.getRandDamage())), 2, 3);
        pane.add(hBox, 0, 4, REMAINING, 1);
        pane.add(list, 0, 5, REMAINING, 1);
        pane.add(textArea, 0, 6, REMAINING, 1);
    }

    @Override
    public void showAction(GAction action) {
        selectedActionLabel.setText(action.toString());
    }

    @Override
    public void showObjName(Selectable obj) {
        String s = obj != null
                ? obj.toString()
                : " - ";
        selectedObjLabel.setText(s);
    }

    @Override
    public void showTurnNumber() {
        turnLabel.setText(Integer.toString(model.getHour()));
    }

    @Override
    public void log(String s) {
        gameLog.appendText(s + "\n");
    }

    @Override
    public void showActivePlayer() {
        final Player player = GameModel.MODEL.getActivePlayer();
        selectedPlayer.setText(String.valueOf(player));
        selectedPlayer.setTextFill(player.getColor());
    }

    @Override
    public void createVisualizerFor(GObject obj) {
        if (obj != null) {
            final GObjectVisualizerImpl visualizer = new GObjectVisualizerImpl(obj, this);
            obj.setVisualizer(visualizer);
            visualizer.create(obj.getPlace());
        }
    }

    @Override
    public void error(String s) {
        final Stage dialog = createDialog();
        dialog.setScene(
                new Scene(
                        VBoxBuilder.create().styleClass("modal-dialog").children(
                                ButtonBuilder.create().text(s).defaultButton(true).onAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        dialog.close();
                                    }
                                }).build()
                        ).build(),
                        Color.GRAY
                )
        );
        dialog.showAndWait();
    }

    @Override
    public UnitSelector createUnitSelector(List<GUnit> units) {
        final Stage dialog = createDialog();
        dialog.initModality(Modality.NONE);
        return new UnitSelectorImpl(units, dialog);
    }

    private Stage createDialog() {
        final Stage dialog = new Stage(StageStyle.TRANSPARENT);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(getScene().getWindow());
        return dialog;
    }

    public BoardCell getBoardCell(GameCell currentCell) {
        return cells.get(currentCell);
    }

    public GridPane getBoardPane() {
        return boardPane;
    }
}
