package sample.Core;

import javafx.animation.PauseTransition;
import sample.Core.Phase.CreationPhase;
import sample.Core.Phase.GPhase;
import sample.*;
import sample.Events.EndHourEvent;
import sample.GActions.*;
import sample.Graphics.GraphicsHelper;

import java.util.*;


public class GameModel {
    public static final GameModel MODEL = new GameModel();
    public static final AbstractGAction SELECT_ACTION = SelectAction.getInstance();
    private AbstractGAction currentPhaseAction;
    Set<GObject> objects = new HashSet<>();
    private GAction[] possibleActions;
    private GAction selectedAction;
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
    public Board board;

    public void init() {
        board = new Board(14, 8);
        possibleActions = new GAction[]{
                new ChangeOwnerAction(),
                new ShiftAction(),
                new KillAction(),
                new EndHourAction()};
        selectedAction = possibleActions[0];
    }

    public void generateObject(ObjectType objectType, int x, int y, int playerIndex) {
        final GObject obj = GObjectFactory.create(objectType);
        obj.setPlayer(getPlayerByIndex(playerIndex));
        createObj(obj, board.get(XY.get(x, y)));
    }

    public void generateUnit(String unitType, int x, int y, int playerIndex) {
        final GObject obj = GUnitFactory.create(unitType);
        obj.setPlayer(getPlayerByIndex(playerIndex));
        createObj(obj, board.get(XY.get(x, y)));
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
        for (GlobalMod mod2 : obj.getGlobalMods()) {
            mod2.register(obj);
        }
        graphics.createVisualizerFor(obj);
    }


    public Board getBoard() {
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

    public GAction[] getActions() {
        return possibleActions;
    }

    public void select(Selectable obj) {
        if (obj instanceof GObject) {
            selectedObj = null;
        }
        showInfo(obj);
    }

    public void showInfo(Selectable obj) {
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
        new EndHourEvent(hour).process();
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

    public Player getActivePlayer() {
        return activePlayer;
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

    public Set<GUnit> getNearUnits(XY xy) {
        Set<GUnit> unitList;
        unitList = new HashSet<>();
        for (GObject object : objects) {
            if (object instanceof GUnit) {
                GUnit gUnit = (GUnit) object;
                if (XY.isNear(xy, gUnit.getXy())) {
                    unitList.add(gUnit);
                }
            }
        }
        return unitList;
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
            object.onStartHour();
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

    /*-----------------GETTING OBJECTS------------------------*/
    public List<GObject> getObjects(Collection<GFilter> filters) {
        List<GObject> result = new ArrayList<>(objects);
        for (GFilter filter : filters) {
            filter.filter(result);
        }
        return result;
    }

    public List<GObject> getObjects(GFilter... filters) {
        List<GFilter> filterList = new ArrayList<>();
        Collections.addAll(filterList, filters);
        return getObjects(filterList);
    }

    public List<GameCell> getCells(List<GFilter> filters) {
        List<GameCell> cells = new ArrayList<>(board.getAllCells());
        for (GFilter filter : filters) {
            filter.filter(cells);
        }
        return cells;
    }

    public List<PlaceHaving> getAll(List<GFilter> filters) {
        List<PlaceHaving> result = new ArrayList<>();
        result.addAll(getCells(filters));
        result.addAll(getObjects(filters));
        return result;

    }

    /*--------------------------GAME CHECKS---------------------*/
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

    public List<GObject> getObjectsByCircle(XY attackerPlace, XY firstAttackedPlace, int times) {
        if (!attackerPlace.isNear(firstAttackedPlace)) {
            throw new IllegalArgumentException("firstAttackedPlace must be near attackerPlace");
        }
        ArrayList<GObject> result = new ArrayList<>();
        List<XY> places = attackerPlace.getPlacesByCircle(firstAttackedPlace, true, times);
        places.forEach(p -> {
            GameCell gameCell = board.get(p);
            if (gameCell != null) {
                GObject obj = gameCell.getObj();
                if (obj != null) {
                    result.add(obj);
                }
            }
        });

        return result;
    }

    public MainVisualizer getGraphics() {
        return graphics;
    }
}
