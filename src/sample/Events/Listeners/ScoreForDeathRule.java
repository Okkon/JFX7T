package sample.Events.Listeners;

import sample.Core.*;
import sample.Events.ScoreChangeEvent;
import sample.Events.UnitDeathEvent;
import sample.MyConst;


public class ScoreForDeathRule<T extends UnitDeathEvent> extends GEventListener<T> {
    @Override
    public void doAfterEvent(UnitDeathEvent event) {
        Hit hit = event.getHit();
        if (hit == null || hit.getAttacker() == null) {
            return;
        }
        GUnit unit = event.getUnit();
        final GObject attacker = hit.getAttacker();
        if (attacker != null && attacker.getPlayer() != null) {
            final Player attackerPlayer = attacker.getPlayer();
            if (!attackerPlayer.equals(Player.NEUTRAL)) {
                int score = MyConst.SCORE_FOR_UNIT;
                attackerPlayer.score(attackerPlayer.isOwnerFor(unit) ? -score : score);
                new ScoreChangeEvent(attackerPlayer, score, unit.getPlace()).process();
            }
        }
    }
}
