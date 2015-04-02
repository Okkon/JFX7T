package sample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kondrashov on 18.02.2015.
 */
public class Tower extends GObject{

    public Tower() {
    }

    @Override
    public void takeHit(Hit hit) {

    }

    @Override
    public void endHour() {
        super.endHour();
        attack();
        checkControl();
    }

    private void attack() {
        if (getPlayer().equals(Player.NEUTRAL)) {
            return;
        } else {
            final List<GUnit> enemies = GameModel.MODEL.getEnemiesNear(getPlace(), getPlayer());
            for (GUnit nearUnit : enemies) {
                for (GMod mod : nearUnit.getMods()) {
                    if (mod.blocksTower()) {
                        return;
                    }
                }
            }
            for (GUnit enemy : enemies) {
                final Hit hit = Hit.createHit(this, enemy, 1, 2);
                enemy.takeHit(hit);
            }
        }
    }

    @Override
    public void endTurn() {
    }

    private void checkControl() {
        Map<Player, Integer> controlPower = new HashMap<Player, Integer>();
        Set<GUnit> unitSet = GameModel.MODEL.getNearUnits(getPlace());
        for (GUnit unit : unitSet) {
            final Player unitPlayer = unit.getPlayer();
            Integer currentPower = controlPower.get(unitPlayer);
            if (currentPower == null) {
                currentPower = 0;
            }
            currentPower += unit.getControlPower();
            controlPower.put(unitPlayer, currentPower);
        }
        int maxPower = 0;
        int overPower = 0;
        Player dominator = null;
        for (Map.Entry<Player, Integer> entry : controlPower.entrySet()) {
            final Integer playerPower = entry.getValue();
            if (playerPower > maxPower) {
                overPower = playerPower - maxPower;
                maxPower = playerPower;
                dominator = entry.getKey();
            } else if (maxPower - playerPower < overPower) {
                overPower = maxPower - playerPower;
            }
        }
        if (overPower > 0 && dominator != getPlayer()) {
            if (getPlayer().equals(Player.NEUTRAL) || overPower > 1) {
                setPlayer(dominator);
            } else {
                setPlayer(Player.NEUTRAL);
            }
        }
    }


    @Override
    public String toString() {
        return "To";
    }
}
