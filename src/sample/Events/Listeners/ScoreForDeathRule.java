package sample.Events.Listeners;

import sample.Core.*;
import sample.Events.UnitDeathEvent;
import sample.MyConst;


public class ScoreForDeathRule<T extends UnitDeathEvent> extends GEventListener<UnitDeathEvent> {
    @Override
    public void doAfterEvent(UnitDeathEvent event) {
        UnitDeathEvent deathEvent = event;
        Hit hit = deathEvent.getHit();
        if (hit == null || hit.getAttacker() == null) {
            return;
        }
        GUnit unit = deathEvent.getUnit();
        final GObject attacker = hit.getAttacker();
        if (attacker != null && attacker.getPlayer() != null) {
            final Player attackerPlayer = attacker.getPlayer();
            if (!attackerPlayer.equals(Player.NEUTRAL)) {
                int score = MyConst.SCORE_FOR_UNIT;
                attackerPlayer.score(attackerPlayer.isOwnerFor(unit) ? -score : score);
            }
        }

    }
}
