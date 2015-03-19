package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GamePanel extends GridPane implements Visualizer {
    private GameModel model;
    private Set<BoardCell> cells;
    private VBox actionPanel;
    private GridPane infoPanel;
    private BorderPane controlPanel;
    private GridPane boardPane;
    private final TextArea gameLog;
    private Label selectedObjLabel = new Label();
    private Label selectedActionLabel = new Label();
    private Label selectedPlayer = new Label();
    private Label turnLabel = new Label();

    public GamePanel(GameModel gameModel) {
        gameModel.setGraphics(this);
        this.model = gameModel;
        initComponents();

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
        infoPanel = new GridPane();
        infoPanel.add(new Label("Selected Obj - "), 0, 0);
        infoPanel.add(selectedObjLabel, 1, 0);
        infoPanel.add(new Label("Selected Action - "), 0, 1);
        infoPanel.add(selectedActionLabel, 1, 1);
        infoPanel.add(new Label("Selected Player - "), 0, 2);
        infoPanel.add(selectedPlayer, 1, 2);
        infoPanel.add(new Label("Turn - "), 0, 3);
        infoPanel.add(turnLabel, 1, 3);

        actionPanel = new VBox(5);
        for (GAction action : GameModel.MODEL.getActions()) {
            addAction(action);
        }

        controlPanel = new BorderPane();
        controlPanel.getStyleClass().add("control-panel");
        controlPanel.setBottom(actionPanel);
        controlPanel.setTop(infoPanel);
    }

    private void initBoard() {
        boardPane = new GridPane();
        boardPane.setGridLinesVisible(true);
        cells = new HashSet<BoardCell>();
        final Map<XY, GameCell> board = model.getBoard();
        for (Map.Entry<XY, GameCell> entry : board.entrySet()) {
            final BoardCell cell = new BoardCell(entry.getValue());
            final int cellSize = 35;
            cell.setMinSize(cellSize, cellSize);
            cell.setPrefSize(cellSize, cellSize);
            cell.setMaxSize(cellSize, cellSize);
            cell.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    BoardCell selectedCell = (BoardCell) mouseEvent.getSource();
                    model.press(selectedCell.getGameCell());
                    refresh();
                }
            });
            boardPane.add(cell, entry.getKey().getX(), entry.getKey().getY());
            cells.add(cell);
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
        GameCell obj = null;
        if (model.getSelectedObj() instanceof GameCell) {
            obj = (GameCell) model.getSelectedObj();
        }
        final BoardCell lastSelected = BoardCell.getLastSelected();
        if (lastSelected != null) {
            lastSelected.deselect();
        }
        for (BoardCell cell : cells) {
            cell.visualize();
            if (obj != null && obj.equals(cell.getGameCell())) {
                cell.select();
            }
        }
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
                                LabelBuilder.create().text("Choose Unit Type").build(),
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
    public void showInfo(Selectable obj) {


    }

    @Override
    public void showAction(GAction action) {
        selectedActionLabel.setText(action.toString());
    }

    @Override
    public void selectObj(Selectable obj) {
        String s = obj != null
                ? obj.toString()
                : " - ";
        selectedObjLabel.setText(s);
    }

    @Override
    public void updateTurnNumber() {
        turnLabel.setText(Integer.toString(model.getTurn()));
    }

    @Override
    public void log(String s) {
        gameLog.appendText(s + "\n");
    }

    private Stage createDialog() {
        final Stage dialog = new Stage(StageStyle.TRANSPARENT);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(getScene().getWindow());
        return dialog;
    }
}
