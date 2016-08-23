package sample.Core.Scenario;

import sample.Core.Phase.GamePhase;

import static sample.GUnitFactory.UnitType.*;

public class Scenario1 extends BaseScenario {
    @Override
    public void locateUnits() {
        generateUnit(Archer, 3, 1, 0);
        generateUnit(Assassin, 4, 4, 0);
        generateUnit(Mage, 4, 6, 0);
        generateUnit(Inquisitor, 5, 3, 0);
        generateUnit(Footman, 6, 6, 0);
        generateUnit(Troll, 6, 4, 0);

        generateUnit(Archer, 9, 1, 1);
        generateUnit(Assassin, 7, 1, 1);
        generateUnit(Mage, 10, 4, 1);
        generateUnit(Inquisitor, 8, 6, 1);
        generateUnit(Footman, 7, 2, 1);
        generateUnit(AstralArcher, 9, 3, 1);
    }

    @Override
    public void startGame() {
        model.setPhase(new GamePhase());
//        setPhase(new CreationPhase());
    }


}
