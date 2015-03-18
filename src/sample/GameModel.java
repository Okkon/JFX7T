package sample;

import javafx.scene.paint.Color;

import java.util.*;


public class GameModel {
    Set<GObject> objects = new HashSet<GObject>();
    public static GameModel MODEL = new GameModel();
    private GAction[] possibleActions = {new SelectAction(), new ShiftAction(), new CreateAction(), new EndTurnAction(), new EndHourAction()};

    private GAction selectedAction = possibleActions[0];
    private Map<XY, GameCell> board = new HashMap<XY, GameCell>();
    private Visualizer graphics;
    private Selectable selectedObj;
    private List<Player> players;
    private Player activePlayer;
    private int turn = 1;
    private Collection<? extends Selectable> possibleSelection;

    private GameModel() {
        initPlayers();
    }

    private void initPlayers() {
        players = new ArrayList<Player>();
        players.add(new Player("1", Color.AZURE));
        players.add(new Player("2", Color.CORAL));
        activePlayer = players.get(0);
    }

    public void press(Selectable aim) {
        if (selectedAction != null) {
            selectedAction.act(aim);
        }
    }

    public void createUnit(UnitType type, GameCell cell) {
        if (cell == null || cell.getObj() != null) {
            return;
        }
        GObject obj = GObjectFactory.create(type);
        obj.setPlace(cell);
        obj.setPlayer(activePlayer);
        cell.setObj(obj);
        objects.add(obj);
        refresh();
    }

    private void refresh() {
        this.graphics.refresh();
    }

    public Map<XY, GameCell> getBoard() {
        return board;
    }

    public void setAction(GAction action) {
        this.selectedAction = action;
        action.onSelect();
        graphics.showAction(action);
    }

    public void setGraphics(Visualizer graphics) {
        this.graphics = graphics;
    }

    public void setBoard(int x, int y) {
        board.clear();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                final XY xy = new XY(i, j);
                board.put(xy, new GameCell(xy));
            }
        }
    }

    public GAction[] getActions() {
        return possibleActions;
    }

    public Selectable getSelectedObj() {
        return selectedObj;
    }

    public void select(Selectable obj) {
        this.selectedObj = obj;
        obj.select(GAction.DefaultAction);
        graphics.selectObj(obj);
        graphics.showInfo(obj);
    }

    public void visualize() {
        graphics.refresh();
    }

    public UnitType createUnitChooser() {
        return graphics.createUnitChooser();
    }

    public void cancel() {
        this.setAction(GAction.DefaultAction);
        showSelectionPossibility(null);
        selectedObj = null;
        visualize();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void endTurn() {
        cancel();
        if (!someoneCanAct()) {
            endHour();
        }
        passTurn();
    }

    private void passTurn() {
        activePlayer = nextPlayer();
        if (activePlayer.getActiveUnits().isEmpty()) {
            passTurn();
        }
    }

    private Player nextPlayer() {
        int index = players.indexOf(activePlayer);
        index++;
        if (index >= players.size()) {
            index = 0;
        }
        return players.get(index);
    }

    private boolean someoneCanAct() {
        for (GObject object : objects) {
            if (object.canAct()) {
                return true;
            }
        }
        return false;
    }

    public void endHour() {
        turn++;
        graphics.updateTurnNumber();
    }

    public int getTurn() {
        return turn;
    }

    public Set<GObject> getObjects() {
        return objects;
    }

    public static Collection<Way> findAllWays(GUnit unit, MoveType moveType) {
        Map<GameCell, Way> destinations = new HashMap<GameCell, Way>();
        Way start = new Way(unit.getPlace(), unit.getMP());

        Set<Way> ways = moveType.getWayPoints(start, unit);
        for (Way way : ways) {
            final Way shortestWay = destinations.get(way.getCell());
            if (shortestWay == null || way.getMp() > shortestWay.getMp()) {
                destinations.put(way.getCell(), way);
            }
        }

        return destinations.values();
    }


    public List<GameCell> getNearCells(GameCell cell) {
        List<GameCell> cells = new ArrayList<GameCell>();
        XY xy = cell.getXy();
        for (GameCell gameCell : board.values()) {
            if (XY.isNear(xy, gameCell.getXy())) {
                cells.add(gameCell);
            }
        }
        return cells;
    }

    public void showSelectionPossibility(Collection<? extends Selectable> objects) {
        if (possibleSelection != null) {
            for (Selectable selectable : possibleSelection) {
                selectable.hideSelecetionPossibility();
            }
        }
        this.possibleSelection = objects;
        if (objects != null) {
            for (Selectable object : objects) {
                object.showSelectionPossibility();
            }
        }
    }
}
