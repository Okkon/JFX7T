package sample;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Scenario extends AbstractScenario {

    @Override
    public void initPlayers() {
        final List<Player> players = model.getPlayers();
        final Player p1 = new Player("P1", Color.RED);
        p1.setImage(ImageHelper.getPlayerImage("lan"));
        List<GUnit> commonUnits = new ArrayList<GUnit>();
        commonUnits.add((GUnit) GObjectFactory.create(UnitType.Archer));
        commonUnits.add((GUnit) GObjectFactory.create(UnitType.Assassin));
        commonUnits.add((GUnit) GObjectFactory.create(UnitType.Footman));
        commonUnits.add((GUnit) GObjectFactory.create(UnitType.Inquisitor));
        commonUnits.add((GUnit) GObjectFactory.create(UnitType.Mage));
        final List<GUnit> p1AvailableUnits = p1.getAvailableUnits();
        for (GUnit gUnit : commonUnits) {
            p1AvailableUnits.add(gUnit.copy());
        }
        p1AvailableUnits.add((GUnit) GObjectFactory.create(UnitType.Troll));
        for (GUnit unit : p1AvailableUnits) {
            unit.setPlayer(p1);
        }
        final Player p2 = new Player("P2", Color.DARKGREEN);
        p2.setImage(ImageHelper.getPlayerImage("mor"));
        //p2.setAI(true);
        final List<GUnit> p2AvailableUnits = p2.getAvailableUnits();
        for (GUnit gUnit : commonUnits) {
            p2AvailableUnits.add(gUnit.copy());
        }
        p2AvailableUnits.add((GUnit) GObjectFactory.create(UnitType.AstralArcher));
        for (GUnit unit : p2AvailableUnits) {
            unit.setPlayer(p2);
        }
        players.add(p1);
        players.add(p2);
    }

    @Override
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

    @Override
    public void startGame() {
        model.setPhase(new GamePhase());
//        setPhase(new CreationPhase());
    }


}
