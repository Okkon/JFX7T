package sample;

import sample.Events.TowerOwnerChangeEvent;
import sample.GActions.Crossbow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Tower extends GObject{

    public Tower() {
    }

    @Override
    public int takeHit(Hit hit) {
        return 0;
    }

    @Override
    public void endHour() {
        super.endHour();
        attack();
        checkControl();
        score();
    }

    private void score() {
        final int hour = GameModel.MODEL.getHour();
        final Player owner = getPlayer();
        if (hour % 3 == 0 && owner != null && !owner.equals(Player.NEUTRAL)) {
            owner.score(hour / 3);
        }
    }

    private void attack() {
        if (getPlayer().equals(Player.NEUTRAL)) {
            return;
        } else {
            final List<GUnit> enemies = GameModel.MODEL.getEnemiesNear(getPlace(), getPlayer());
            for (GUnit nearUnit : enemies) {
                for (GMod mod : nearUnit.getMods()) {
                    if (mod.blocksTower(this)) {
                        return;
                    }
                }
            }
            Crossbow crossbow = new Crossbow(1, 2, XY.diagonalLength);
            crossbow.setOwner(this);
            for (GUnit enemy : enemies) {
                GameModel.MODEL.log("base", "Hits", this, enemy);
                crossbow.act(enemy);
            }
        }
    }

    @Override
    public boolean blocksMoveFor(GUnit unit) {
        return true;
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
                changePlayer(dominator);
            } else {
                changePlayer(Player.NEUTRAL);
            }
        }
    }

    private void changePlayer(Player player) {
        final TowerOwnerChangeEvent event = new TowerOwnerChangeEvent(this, player);
        event.process();
    }


    @Override
    public String toString() {
        return "To" + (place != null ? place.getXy().toString() : "");
    }
}
