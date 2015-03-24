package sample;

import javafx.scene.paint.Color;

import java.util.*;


public class GameModel {
    private static Collection<Way> lastFoundWays;
    Set<GObject> objects = new HashSet<GObject>();
    public static GameModel MODEL = new GameModel();
    private GAction[] possibleActions = {new SelectAction(), new ShiftAction(), new CreateAction()};
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

    public void locateUnits(){
        generateUnit(UnitType.Tower, 3, 4, 0);
        generateUnit(UnitType.Tower, 4, 7, 0);
        generateUnit(UnitType.Tower, 5, 1, 2);
        generateUnit(UnitType.Tower, 7, 4, 2);
        generateUnit(UnitType.Tower, 8, 1, 1);
        generateUnit(UnitType.Tower, 8, 7, 1);
        generateUnit(UnitType.Tower, 10, 3, 1);

        generateUnit(UnitType.Archer, 3, 1, 0);
        generateUnit(UnitType.Assassin, 4, 4, 0);
        generateUnit(UnitType.Mage, 4, 6, 0);
        generateUnit(UnitType.Inquisitor, 5, 3, 0);
        generateUnit(UnitType.Footman, 6, 6, 0);

        generateUnit(UnitType.Archer, 9, 1, 1);
        generateUnit(UnitType.Assassin, 7, 1, 1);
        generateUnit(UnitType.Mage, 10, 4, 1);
        generateUnit(UnitType.Inquisitor, 8, 6, 1);
        generateUnit(UnitType.Footman, 7, 2, 1);
    }

    private void generateUnit(UnitType unitType, int x, int y, int playerCode) {
        final GObject obj = GObjectFactory.create(unitType);
        obj.setPlayer(players.get(playerCode));
        createUnit(obj, board.get(new XY(x, y)));
    }

    private void initPlayers() {
        players = new ArrayList<Player>();
        players.add(new Player("P1", Color.AZURE));
        players.add(new Player("P2", Color.CORAL));
        players.add(Player.NEUTRAL);
        activePlayer = players.get(0);
    }

    public void press(GameCell cell) {
        final GObject obj = cell.getObj();
        if (obj != null && selectedAction.canSelect(obj)) {
            selectedAction.act(obj);
            return;
        }
        if (selectedAction.canSelect(cell)) {
            selectedAction.act(cell);
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
            this.selectedObj = (GObject) obj;
        }
        obj.select();
        graphics.selectObj(obj);
        graphics.showInfo(obj);
        visualize();
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
        } else {
            passTurn();
        }
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

    public boolean isNear(GObject obj1, GObject obj2) {
        return XY.isNear(obj1.getXy(), obj2.getXy());
    }

    public void error(String s) {
        graphics.error(s);
    }

    public Set<GUnit> getNearUnits(GameCell cell) {
        Set<GUnit> unitSet = new HashSet<GUnit>();
        for (GObject object : objects) {
            if (object instanceof GUnit) {
                GUnit gUnit = (GUnit) object;
                if (XY.isNear(cell.getXy(), gUnit.getXy())) {
                    unitSet.add(gUnit);
                }
            }
        }
        return unitSet;
    }

    public boolean canSee(GObject observer, GObject aim) {
        return true;
    }

    public boolean onOneLine(GObject obj, GObject obj1) {
        return XY.isOnOneLine(obj.getXy(), obj1.getXy());
    }

    public GameCell getNextCell(GameCell cell, Direction direction) {
        final XY currentPlace = cell.getXy();
        final XY newPlace = XY.step(currentPlace, direction);
        return board.get(newPlace);
    }
}
