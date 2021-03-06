package sample.Core.Scenario;

import javafx.scene.paint.Color;
import sample.Core.AbstractScenario;
import sample.Core.GEvent;
import sample.Core.GUnit;
import sample.Core.Phase.CreationPhase;
import sample.Core.Player;
import sample.Events.EndHourEvent;
import sample.Events.Listeners.ScoreForDeathRule;
import sample.Events.Listeners.ScoreForTowers;
import sample.Events.UnitDeathEvent;
import sample.GUnitFactory;
import sample.Helpers.ImageHelper;

import java.util.ArrayList;
import java.util.List;

import static sample.Core.ObjectType.MainTower;
import static sample.Core.ObjectType.Tower;
import static sample.GUnitFactory.UnitClass.*;

public class BaseScenario extends AbstractScenario {

    @Override
    public void initPlayers() {
        final List<Player> players = model.getPlayers();
        final Player p1 = new Player("P1", Color.RED);
        p1.setImage(ImageHelper.getPlayerImage("lan"));
        List<GUnit> commonUnits = new ArrayList<>();
        for (GUnitFactory.UnitClass unitClass : values()) {
            if (unitClass.type == UnitType.base) {
                commonUnits.add(GUnitFactory.create(unitClass));
            }
        }
        final List<GUnit> p1AvailableUnits = p1.getAvailableUnits();
        for (GUnit gUnit : commonUnits) {
            p1AvailableUnits.add(gUnit.copy());
        }
        p1AvailableUnits.add(GUnitFactory.create(Troll));
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
        p2AvailableUnits.add(GUnitFactory.create(AstralArcher));
        for (GUnit unit : p2AvailableUnits) {
            unit.setPlayer(p2);
        }
        players.add(p1);
        players.add(p2);

        GEvent.addListener(UnitDeathEvent.class, new ScoreForDeathRule());
        GEvent.addListener(EndHourEvent.class, new ScoreForTowers());
    }

    public void locateTowers() {
        generateObject(MainTower, 0, 4, 0);
        generateObject(MainTower, 13, 5, 1);

        generateObject(Tower, 3, 4, 0);
        generateObject(Tower, 4, 7, 0);
        generateObject(Tower, 5, 1, 2);
        generateObject(Tower, 7, 4, 2);
        generateObject(Tower, 8, 0, 1);
        generateObject(Tower, 8, 7, 1);
        generateObject(Tower, 10, 3, 1);
    }

    @Override
    public void locateUnits() {
        //doNothing
    }

    @Override
    public void startGame() {
//        model.setPhase(new GamePhase());
        model.setPhase(new CreationPhase());
    }
}
