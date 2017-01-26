package sample.Tower;

import sample.Core.*;
import sample.Events.HealEvent;
import sample.Events.OwnerChangeEvent;
import sample.Helpers.GameHelper;
import sample.Skills.Crossbow;
import sample.XY;

import java.util.*;
import java.util.stream.Collectors;

public class Tower extends GObject {

    public Tower() {
    }

    @Override
    public int takeHit(Hit hit) {
        return 0;
    }

    @Override
    public void onEndHour() {
        super.onEndHour();
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
                    if (mod.blocksTower(this)) {
                        return;
                    }
                }
            }
            Crossbow crossbow = new Crossbow(1, 2, XY.diagonalLength);
            crossbow.setActor(this);
            GUnit enemy = GameHelper.getRandomFromCollection(enemies);
            if (enemy != null) {
                GameModel.MODEL.log("base", "Hits", this, enemy);
                crossbow.getAims().add(enemy);
                crossbow.doAction();
            }

            if (enemies.isEmpty()) {
                final List<GUnit> friends = new ArrayList<>();
                final Set<GUnit> nearUnits = GameModel.MODEL.getNearUnits(getPlace());
                friends.addAll(nearUnits.stream().filter(nearUnit -> nearUnit.isFriendlyFor(this)).collect(Collectors.toList()));
                for (GUnit friend : friends) {
                    new HealEvent(friend, 2).process();
                }
            }

        }
    }

    @Override
    public boolean blocksMoveFor(GUnit unit) {
        return true;
    }

    private void checkControl() {
        Map<Player, Integer> controlPower = new HashMap<>();
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
        final OwnerChangeEvent event = new OwnerChangeEvent(this, player);
        event.process();
    }


    @Override
    public String toString() {
        return "To" + (place != null ? place.getXy().toString() : "");
    }
}
