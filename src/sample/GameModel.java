package sample;

import javafx.scene.paint.Color;

import java.util.*;


public class GameModel {
    public static final GAction SELECT_ACTION = new SelectAction();
    private Collection<Way> lastFoundWays;
    Set<GObject> objects = new HashSet<GObject>();
    public static GameModel MODEL = new GameModel();
    private GAction[] possibleActions = {new SelectAction(), new ShiftAction(), new CreateAction()};
    private GAction selectedAction = possibleActions[0];
    private Map<XY, GameCell> board = new HashMap<XY, GameCell>();
    private MainVisualizer graphics;
    private GObject selectedObj;
    private List<Player> players;
    private Player activePlayer;
    private int hour = 0;
    private Collection<? extends Selectable> possibleSelection;
    private GObject lastActedUnit;

    public void init() {
        initPlayers();
        setBoard(14, 8);
    }

    public void locateUnits() {
        generateUnit(UnitType.MainTower, 0, 4, 0);
        generateUnit(UnitType.MainTower, 13, 5, 1);

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

    private void generateUnit(UnitType unitType, int x, int y, int playerIndex) {
        final GObject obj = GObjectFactory.create(unitType);
        obj.setPlayer(players.get(playerIndex));
        createObj(obj, board.get(new XY(x, y)));
    }

    private void initPlayers() {
        players = new ArrayList<Player>();
        final Player p1 = new Player("P1", Color.RED /*Color.AZURE*/);
        List<GUnit> commonUnits = new ArrayList<GUnit>();
        commonUnits.add((GUnit) GObjectFactory.create(UnitType.Archer));
        commonUnits.add((GUnit) GObjectFactory.create(UnitType.Assassin));
        commonUnits.add((GUnit) GObjectFactory.create(UnitType.Footman));
        commonUnits.add((GUnit) GObjectFactory.create(UnitType.Inquisitor));
        commonUnits.add((GUnit) GObjectFactory.create(UnitType.Mage));
        final List<GUnit> p1AvailableUnits = p1.getAvailableUnits();
        p1AvailableUnits.addAll(commonUnits);
        p1AvailableUnits.add((GUnit) GObjectFactory.create(UnitType.Troll));
        for (GUnit unit : p1AvailableUnits) {
            unit.setPlayer(p1);
        }
        final Player p2 = new Player("P2", Color.CORAL);
        final List<GUnit> p2AvailableUnits = p2.getAvailableUnits();
        p2AvailableUnits.addAll(commonUnits);
        p2AvailableUnits.add((GUnit) GObjectFactory.create(UnitType.AstralArcher));
        for (GUnit unit : p2AvailableUnits) {
            unit.setPlayer(p2);
        }
        players.add(p1);
        players.add(p2);
        players.add(Player.NEUTRAL);
    }

    public void press(Selectable obj) {
        selectedAction.perform(obj);
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
            action.onSelect();
            graphics.showAction(action);
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
        if (obj != null) {
            if (obj instanceof GObject) {
                final GObject gObject = (GObject) obj;
                if (canBeSelected(gObject)) {
                    if (selectedObj != null) {
                        selectedObj.getVisualizer().setSelected(false);
                    }
                    selectedObj = gObject;
                    selectedObj.getVisualizer().setSelected(true);
                    setAction(selectedObj.getBaseAction());
                }
            }
            if (obj instanceof MainTower) {
                MainTower mainTower = (MainTower) obj;
                setAction(mainTower.baseAction);
            }
        }
        graphics.showObjName(obj);
        graphics.showObjInfo(obj);
    }

    protected boolean canBeSelected(GObject gObject) {
        boolean canBeSelected = activePlayer.equals(gObject.getPlayer()) && gObject.canAct();
        if (canBeSelected && lastActedUnit != null && !lastActedUnit.equals(gObject) && activePlayer.isOwnerFor(lastActedUnit)) {
            canBeSelected = false;
            error("errorText", "OtherUnitActed");
        }
        return canBeSelected;
    }

    public GObject createUnitCreationPanel() {
        return graphics.createUnitCreationPanel();
    }

    public void cancel() {
        showSelectionPossibility(null);
        select(null);
        if (selectedObj != null) {
            selectedObj.getVisualizer().setSelected(false);
        }
        this.setAction(SELECT_ACTION);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void endTurn() {
        if (selectedObj != null) {
            selectedObj.endTurn();
            log("base", "EndsTurn", selectedObj);
        }
        log("base", "EndTurnSymbol");
        final Player nextPlayer = getNextPlayer();
        if (nextPlayer == null) {
            endHour();
            startHour();
        } else {
            setActivePlayer(nextPlayer);
            setLastActedUnit(null);
        }
        cancel();
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

    protected void setActivePlayer(Player player) {
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
        List<GObject> gObjects = new ArrayList<GObject>();
        gObjects.addAll(getObjects());
        for (GObject gObject : gObjects) {
            gObject.endHour();
        }
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

    public Collection<Way> findAllWays(GUnit unit, MoveType moveType) {
        Map<GameCell, Way> destinations = new HashMap<GameCell, Way>();
        Way start = new Way(unit.getPlace());
        Queue<Way> wayQueue = new ArrayDeque<Way>();
        wayQueue.add(start);

        while (!wayQueue.isEmpty()) {
            final Way wayPoint = wayQueue.poll();
            Set<Way> ways = moveType.getWayFromCell(wayPoint, unit);
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
        List<GUnit> list = new ArrayList<GUnit>();
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
        List<GObject> list = new ArrayList<GObject>();
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
        for (GObject object : objects) {
            object.startHour();
        }
        Random r = new Random();
        setActivePlayer(players.get(r.nextInt(players.size() - 1)));
//        setActivePlayer(players.get(1));
        graphics.showTurnNumber();
        cancel();
    }

    public void setLastActedUnit(GObject lastActedUnit) {
        this.lastActedUnit = lastActedUnit;
    }

    @SuppressWarnings("unused")
    public GObject getLastActedUnit() {
        return lastActedUnit;
    }

    public UnitSelector provideUnitSelector(List<GUnit> units) {
        return graphics.createUnitSelector(units);
    }

    public List<GObject> getObjects(Collection<GFilter> filters) {
        List<GObject> result = new ArrayList<GObject>();
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

    public boolean canAttack(GObject attacker, Selectable aim) {
        for (GMod mod : attacker.getMods()) {
            if (mod.disablesAttack()) {
                return false;
            }
        }
        return true;
    }
}
