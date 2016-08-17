package sample.Core;

import javafx.animation.PauseTransition;
import sample.Core.Phase.CreationPhase;
import sample.Core.Phase.GPhase;
import sample.*;
import sample.GActions.*;
import sample.Graphics.GraphicsHelper;

import java.util.*;


public class GameModel {
    public static final GameModel MODEL = new GameModel();
    public static final AbstractGAction SELECT_ACTION = SelectAction.getInstance();
    private AbstractGAction currentPhaseAction;
    private Collection<Way> lastFoundWays;
    Set<GObject> objects = new HashSet<>();
    private GAction[] possibleActions;
    private GAction selectedAction;
    private Map<XY, GameCell> board = new HashMap<>();
    private MainVisualizer graphics;
    private GObject selectedObj;
    private List<Player> players = new ArrayList<>();
    private Player activePlayer;
    private int hour = 0;
    private Collection<? extends Selectable> possibleSelection;
    private GObject actingUnit;
    private GPhase phase;
    private AbstractScenario scenario;
    private List<GAura> auras = new ArrayList<>();

    public void init() {
        setBoard(14, 8);
        possibleActions = new GAction[]{new ChangeOwnerAction(), new ShiftAction(), new KillAction(), new EndHourAction()};
        selectedAction = possibleActions[0];
    }

    public void generateObject(ObjectType objectType, int x, int y, int playerIndex) {
        final GObject obj = GObjectFactory.create(objectType);
        obj.setPlayer(getPlayerByIndex(playerIndex));
        createObj(obj, board.get(new XY(x, y)));
    }

    public void generateUnit(String unitType, int x, int y, int playerIndex) {
        final GObject obj = GUnitFactory.create(unitType);
        obj.setPlayer(getPlayerByIndex(playerIndex));
        createObj(obj, board.get(new XY(x, y)));
    }

    private Player getPlayerByIndex(int playerIndex) {
        return playerIndex >= players.size() ? Player.NEUTRAL : players.get(playerIndex);
    }

    public void press(PlaceHaving obj) {
        selectedAction.tryToSelect(obj);
    }

    public void createObj(GObject obj, GameCell cell) {
        if (cell == null || cell.getObj() != null) {
            return;
        }
        obj.setPlace(cell);
        cell.setObj(obj);
        objects.add(obj);
        graphics.createVisualizerFor(obj);
    }


    public Map<XY, GameCell> getBoard() {
        return board;
    }

    public void setAction(GAction action) {
        if (action.canBeSelected()) {
            this.selectedAction = action;
            graphics.showAction(action);
            action.onSelect();
        }
    }

    public void setGraphics(MainVisualizer graphics) {
        this.graphics = graphics;
    }

    private void setBoard(int x, int y) {
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

    public void select(Selectable obj) {
        if (obj instanceof GObject) {
            final GObject gObject = (GObject) obj;
            if (selectedObj != null) {
                selectedObj.getVisualizer().setSelected(false);
            }
            selectedObj = gObject;
            selectedObj.getVisualizer().setSelected(true);
            setAction(selectedObj.getBaseAction());
        }
        graphics.showObjInfo(obj);
    }

    public void cancel() {
        selectedAction.cancel();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void endTurn() {
        setActingUnit(null);
        if (selectedObj != null) {
            selectedObj.endTurn();
            log("base", "EndsTurn", selectedObj);
        }
        selectedObj = null;
        log("base", "EndTurnSymbol");
        final Player nextPlayer = getNextPlayer();
        if (nextPlayer == null) {
            endHour();
        } else {
            setActivePlayer(nextPlayer);
        }
        setAction(getPhaseAction());
    }

    private Player getNextPlayer() {
        final Player currentPlayer = activePlayer;
        Player checkingPlayer = currentPlayer;
        do {
            checkingPlayer = nextPlayer(checkingPlayer);
            if (!checkingPlayer.getActiveUnits().isEmpty()) {
                return checkingPlayer;
            }
        } while (!currentPlayer.equals(checkingPlayer));

        return null;
    }

    public void setActivePlayer(Player player) {
        activePlayer = player;
        log("base", "StartsTurn", player);
        graphics.showActivePlayer();
    }

    private Player nextPlayer(Player player) {
        int index = players.indexOf(player);
        index++;
        if (index >= players.size()) {
            index = 0;
        }
        return players.get(index);
    }

    public void endHour() {
        List<GObject> gObjects = new ArrayList<>();
        gObjects.addAll(getObjects());
        for (GObject gObject : gObjects) {
            gObject.endHour();
        }
        log("base", "HourEnds", hour);
        final GraphicsHelper instance = GraphicsHelper.getInstance();
        final PauseTransition transition = new PauseTransition();
        transition.setOnFinished(event -> setPhase(new CreationPhase()));
        instance.addTransition(transition);
        instance.play();
    }

    public int getHour() {
        return hour;
    }

    public Set<GObject> getObjects() {
        return objects;
    }

    public Collection<Way> getLastFoundWays() {
        return lastFoundWays;
    }

    public Collection<Way> findAllWays(GUnit unit, MoveAction moveAction) {
        Map<GameCell, Way> destinations = new HashMap<>();
        Way start = new Way(unit.getPlace());
        Queue<Way> wayQueue = new ArrayDeque<>();
        wayQueue.add(start);

        while (!wayQueue.isEmpty()) {
            final Way wayPoint = wayQueue.poll();
            Set<Way> ways = moveAction.getWayFromCell(wayPoint, unit);
            for (Way way : ways) {
                final Way shortestWay = destinations.get(way.getDestinationCell());
                if (shortestWay == null || way.getLength() < shortestWay.getLength()) {
                    destinations.put(way.getDestinationCell(), way);
                    wayQueue.add(way);
                }
            }
        }

        final Collection<Way> wayCollection = destinations.values();
        lastFoundWays = wayCollection;
        return wayCollection;
    }


    public List<GameCell> getNearCells(GameCell cell) {
        List<GameCell> cells = new ArrayList<>();
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
                selectable.getVisualizer().setSelectionPossibility(false);
            }
        }
        this.possibleSelection = objects;
        if (objects != null) {
            for (Selectable object : objects) {
                object.getVisualizer().setSelectionPossibility(true);
            }
        }
    }

    public void log(String res, String s, Object... objects) {
        final String message = String.format(ResourceBundle.getBundle(MyConst.RESOURCE_BUNDLE_LOCATION + res).getString(s), objects);
        graphics.log(message);
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

    public boolean isNear(GObject obj1, PlaceHaving obj2) {
        return XY.isNear(obj1.getXy(), obj2.getXy());
    }

    public void error(String s) {
        graphics.error(s);
    }

    public void error(String res, String s, Object... objects) {
        final String message = String.format(ResourceBundle.getBundle(MyConst.RESOURCE_BUNDLE_LOCATION + res).getString(s), objects);
        graphics.error(message);
    }

    public Set<GUnit> getNearUnits(GameCell cell) {
        Set<GUnit> unitSet;
        unitSet = new HashSet<>();
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
        for (GMod mod : aim.getMods()) {
            if (mod.canHideUnit(observer, aim)) {
                return false;
            }
        }
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

    public List<GUnit> getEnemiesNear(GameCell place, Player player) {
        List<GUnit> list = new ArrayList<>();
        final Set<GUnit> nearUnits = getNearUnits(place);
        for (GUnit nearUnit : nearUnits) {
            if (player.isEnemyFor(nearUnit.player)) {
                list.add(nearUnit);
            }
        }
        return list;
    }

    public boolean seesObstacle(GObject observer, GObject aim) {
        List<GObject> gObjects = getObjBetween(observer, aim);
        for (GObject object : gObjects) {
            if (canSee(observer, object)) {
                return true;
            }
        }
        return false;
    }

    private List<GObject> getObjBetween(GObject observer, GObject aim) {
        List<GObject> list = new ArrayList<>();
        final Direction direction = Direction.findDirection(observer.getXy(), aim.getXy());
        GameCell currentCell = getNextCell(observer.getPlace(), direction);
        while (!aim.getPlace().equals(currentCell)) {
            final GObject obj = currentCell.getObj();
            if (obj != null) {
                list.add(obj);
            }
            currentCell = getNextCell(currentCell, direction);
        }
        return list;
    }

    public void startHour() {
        hour++;
        log("base", "EndTurnSymbol");
        graphics.showTurnNumber();
        Player player = getRandomPlayer();
        log("base", "HourStarts", hour, player);
        setActivePlayer(player);
        for (GObject object : objects) {
            object.startHour();
        }
        cancel();
    }

    private Player getRandomPlayer() {
        Random r = new Random();
        return players.get(r.nextInt(players.size()));
    }

    public void setActingUnit(GObject actingUnit) {
        this.actingUnit = actingUnit;
    }

    public GObject getActingUnit() {
        return actingUnit;
    }

    public UnitSelector provideUnitSelector(List<GUnit> units) {
        return graphics.createUnitSelector(units);
    }

    public List<GObject> getObjects(Collection<GFilter> filters) {
        List<GObject> result = new ArrayList<>();
        boolean isOk;
        for (GObject gObject : objects) {
            isOk = true;
            for (GFilter filter : filters) {
                if (!filter.isOk(gObject)) {
                    isOk = false;
                    break;
                }
            }
            if (isOk) {
                result.add(gObject);
            }
        }
        return result;
    }

    public List<GObject> getObjects(GFilter... filters) {
        List<GFilter> filterList = new ArrayList<>();
        Collections.addAll(filterList, filters);
        return getObjects(filterList);
    }

    public boolean canAttack(GObject attacker, @SuppressWarnings("unused") Selectable aim) {
        for (GMod mod : attacker.getMods()) {
            if (mod.disablesAttack()) {
                return false;
            }
        }
        return true;
    }

    public List<GameCell> getCells(List<GFilter> filters) {
        List<GameCell> cells = new ArrayList<>();
        for (GameCell cell : board.values()) {
            boolean isOk = true;
            for (GFilter filter : filters) {
                if (!filter.isOk(cell)) {
                    isOk = false;
                    break;
                }
            }
            if (isOk) {
                cells.add(cell);
            }
        }
        return cells;
    }

    public boolean isInDanger(Selectable obj) {
        if (obj instanceof GObject) {
            GObject checkedUnit = (GObject) obj;
            final Set<GUnit> nearUnits = getNearUnits(((GObject) obj).getPlace());
            for (GUnit nearUnit : nearUnits) {
                if (!nearUnit.isFriendlyFor(checkedUnit)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Player findWeakestPlayer() {
        int minArmy = Integer.MAX_VALUE;
        Player theWeakestPlayer = null;
        for (Player player : getPlayers()) {
            int armySize = player.getUnits().size();
            if (armySize < minArmy) {
                minArmy = armySize;
                theWeakestPlayer = player;
            } else if (armySize == minArmy) {
                theWeakestPlayer = null;
            }
        }
        return theWeakestPlayer;
    }

    public List<PlaceHaving> getAll(List<GFilter> filters) {
        List<PlaceHaving> result = new ArrayList<>();
        result.addAll(getCells(filters));
        result.addAll(getObjects(filters));
        return result;

    }

    public void setPhase(GPhase phase) {
        this.phase = phase;
        phase.init();
    }

    public GPhase getPhase() {
        return phase;
    }

    public void setPhaseAction(AbstractGAction action) {
        this.currentPhaseAction = action;
    }

    public AbstractGAction getPhaseAction() {
        return currentPhaseAction;
    }

    public void startScenario(AbstractScenario scenario) {
        this.scenario = scenario;
        graphics.showTurnNumber();
        scenario.start();
    }

    public GObject getSelectedObj() {
        return selectedObj;
    }

    public List<GAura> getAuras() {
        return auras;
    }
}
