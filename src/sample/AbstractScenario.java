package sample;

public abstract class AbstractScenario {
    protected GameModel model = GameModel.MODEL;

    public void start() {
        initPlayers();
        locateTowers();
        locateUnits();
        startGame();
    }

    protected void generateUnit(UnitType unitType, int x, int y, int playerIndex) {
        model.generateUnit(unitType, x, y, playerIndex);
    }

    public abstract void initPlayers();

    public abstract void locateTowers();

    public abstract void locateUnits();

    public abstract void startGame();
}
