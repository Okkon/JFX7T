package sample.Graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Core.*;
import sample.MyConst;
import sample.Skills.EndTurnAction;
import sample.XY;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePanel extends GridPane implements MainVisualizer {
    private GameModel model;
    private Map<GameCell, BoardCell> cells;
    private VBox actionPanel;
    private GridPane gameInfoPanel;
    private Pane controlPanel;
    private GridPane boardPane;
    private final TextArea gameLog;
    private Label selectedObjLabel = new Label();
    private Label selectedActionLabel = new Label();
    private Label turnLabel = new Label();
    private ObjectInfoPanel objInfoPanel = new ObjectInfoPanel();
    private PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel();

    public GamePanel(GameModel gameModel) {
        gameModel.setGraphics(this);
        this.model = gameModel;
        initComponents();
        setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                model.cancel();
            } else {
                final GObject selectedObj = model.getSelectedObj();
                if (keyEvent.getCode().equals(KeyCode.TAB)) {
                    if (model.getActingUnit() == null) {
                        final List<GUnit> activeUnits = model.getActivePlayer().getActiveUnits();
                        int index = activeUnits.indexOf(selectedObj);
                        index++;
                        if (index >= activeUnits.size()) {
                            index = 0;
                        }
                        model.select(activeUnits.get(index));
                    }
                } else if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    GObject object = selectedObj == null
                            ? model.getActivePlayer().getActiveUnits().get(0)
                            : selectedObj;
                    model.select(object);
                    final EndTurnAction action = new EndTurnAction();
                    action.setOwner(object);
                    action.perform();
                }
            }
        });

        gameLog = new TextArea();
        gameLog.setEditable(false);
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
        gameInfoPanel.add(new Label("Turn - "), 0, 2);
        gameInfoPanel.add(turnLabel, 1, 2);

        actionPanel = new VBox(5);
        for (GAction action : GameModel.MODEL.getActions()) {
            addAction(action);
        }

        controlPanel = VBoxBuilder.create()
                .children(
                        gameInfoPanel,
                        playerInfoPanel,
                        objInfoPanel,
                        actionPanel)
                .styleClass("control-panel")
                .build();
    }

    private void initBoard() {
        boardPane = new GridPane();
        boardPane.setGridLinesVisible(true);
        cells = new HashMap<>();
        final Map<XY, GameCell> board = model.getBoard();
        final EventHandler<MouseEvent> handler = mouseEvent -> {
            BoardCell selectedCell = (BoardCell) mouseEvent.getSource();
            model.press(selectedCell.getGameCell());
            refresh();
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
        button.setOnAction(actionEvent -> GameModel.MODEL.setAction(action));
    }

    @Override
    public void refresh() {
    }

    @Override
    public void showObjInfo(Selectable obj) {
        String s = obj != null
                ? obj.toString()
                : " - ";
        selectedObjLabel.setText(s);
        objInfoPanel.setObj(obj);
    }

    @Override
    public void showAction(GAction action) {
        selectedActionLabel.setText(action.toString());
    }

    @Override
    public void showTurnNumber() {
        turnLabel.setText(Integer.toString(model.getHour()));
    }

    @Override
    public void log(String s) {
        if (gameLog != null) {
            gameLog.appendText(s + "\n");
        }
    }

    @Override
    public void showActivePlayer() {
        final Player player = GameModel.MODEL.getActivePlayer();
        playerInfoPanel.setPlayer(player);
    }

    @Override
    public void createVisualizerFor(GObject obj) {
        if (obj != null) {
            final GObjectVisualizerImpl visualizer = new GObjectVisualizerImpl(obj, this);
            visualizer.create(obj.getPlace());
        }
    }

    @Override
    public void error(String s) {
        final Stage dialog = createDialog();
        dialog.setMinHeight(200);
        dialog.setMinWidth(300);
        dialog.initStyle(StageStyle.UNDECORATED);
        final Scene scene = new Scene(
                BorderPaneBuilder.create().styleClass("modal-dialog").minWidth(300).minHeight(200).center(
                        ButtonBuilder.create().text(s).defaultButton(true).onAction(
                                (EventHandler<ActionEvent>) actionEvent -> dialog.close()).build()
                ).build()
        );
        dialog.setScene(scene);
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
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setResizable(false);
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
