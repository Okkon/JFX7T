package sample.Core;

import sample.GUnitFactory;

public abstract class AbstractScenario {
    protected GameModel model = GameModel.MODEL;

    public void start() {
        initPlayers();
        locateTowers();
        locateUnits();
        startGame();
    }

    protected void generateObject(ObjectType objectType, int x, int y, int playerIndex) {
        model.generateObject(objectType, x, y, playerIndex);
    }

    protected void generateUnit(GUnitFactory.UnitClass unitClass, int x, int y, int playerIndex) {
        model.generateUnit(unitClass.toString(), x, y, playerIndex);
    }

    public abstract void initPlayers();

    public abstract void locateTowers();

    public abstract void locateUnits();

    public abstract void startGame();
}
