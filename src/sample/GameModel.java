package sample;

import javafx.scene.paint.Color;

import java.util.*;


public class GameModel {
    private static Collection<Way> lastFoundWays;
    Set<GObject> objects = new HashSet<GObject>();
    public static GameModel MODEL = new GameModel();
    private GAction[] possibleActions = {new SelectAction(), new ShiftAction(), new CreateAction(), new EndTurnAction()};
    private GAction selectedAction = possibleActions[0];
    private Map<XY, GameCell> board = new HashMap<XY, GameCell>();
    private MainVisualizer graphics;
    private GObject selectedObj;
    private List<Player> players;
    private Player activePlayer;
    private int turn = 1;
    private Collection<? extends Selectable> possibleSelection;

    public void init() {
        initPlayers();
        graphics.showActivePlayer();
        graphics.showTurnNumber();
        cancel();
    }

    private void initPlayers() {
        players = new ArrayList<Player>();
        players.add(new Player("P1", Color.AZURE));
        players.add(new Player("P2", Color.CORAL));
        activePlayer = players.get(0);
    }

    public void press(Selectable aim) {
        if (aim instanceof GameCell) {
            GameCell gameCell = (GameCell) aim;
            final GObject obj = gameCell.getObj();
            if (obj != null && selectedAction.canSelect(obj)) {
                selectedAction.act(obj);
                return;
            }
        }
        if (selectedAction.canSelect(aim)) {
            selectedAction.act(aim);
        }
    }

    public void createUnit(GObject obj, GameCell cell) {
        if (cell == null || cell.getObj() != null) {
            return;
        }
        obj.setPlace(cell);
        cell.setObj(obj);
        objects.add(obj);
        graphics.createVisualizerFor(obj);
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

    public void setGraphics(MainVisualizer graphics) {
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
        if (obj instanceof GObject) {
            GObject gObject = (GObject) obj;
            this.selectedObj = gObject;
        }
        obj.select(GAction.DefaultAction);
        graphics.selectObj(obj);
        graphics.showInfo(obj);
    }

    public void visualize() {
        graphics.refresh();
    }

    public GObject createUnitCreationPanel() {
        return graphics.createUnitCreationPanel();
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
        if (selectedObj != null) {
            selectedObj.endTurn();
        }
        cancel();
        if (!someoneCanAct()) {
            endHour();
        }
        passTurn();
        graphics.showActivePlayer();
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
        graphics.showTurnNumber();
    }

    public int getTurn() {
        return turn;
    }

    public Set<GObject> getObjects() {
        return objects;
    }

    public static Collection<Way> getLastFoundWays() {
        return lastFoundWays;
    }

    public static Collection<Way> findAllWays(GUnit unit, MoveType moveType) {
        Map<GameCell, Way> destinations = new HashMap<GameCell, Way>();
        Way start = new Way(unit.getPlace(), unit.getMP());
        Queue<Way> wayQueue = new ArrayDeque<Way>();
        wayQueue.add(start);

        while (!wayQueue.isEmpty()) {
            Set<Way> ways = moveType.getWayPoints(wayQueue.poll(), unit);
            for (Way way : ways) {
                final Way shortestWay = destinations.get(way.getCell());
                if (shortestWay == null || way.getMp() > shortestWay.getMp()) {
                    destinations.put(way.getCell(), way);
                    wayQueue.add(way);
                }
            }
        }

        final Collection<Way> wayCollection = destinations.values();
        lastFoundWays = wayCollection;
        return wayCollection;
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
                selectable.hideSelectionPossibility();
            }
        }
        this.possibleSelection = objects;
        if (objects != null) {
            for (Selectable object : objects) {
                object.showSelectionPossibility();
            }
        }
    }

    public void log(String s) {
        graphics.log(s);
    }

    public List<GameCell> getEmptyNearCells(GameCell cell) {
        final List<GameCell> nearCells = getNearCells(cell);
        final Iterator<GameCell> iterator = nearCells.iterator();
        while (iterator.hasNext()) {
            GameCell next = iterator.next();
            if (next.isNotEmpty()) {
                iterator.remove();
            }
        }
        return nearCells;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public boolean isNear(GUnit attacker, GObject aim) {
        return XY.isNear(attacker.getPlace().getXy(), aim.getPlace().getXy());
    }

    public void error(String s) {
        graphics.error(s);
    }
}
