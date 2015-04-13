package sample;

import java.util.List;

public class BaseAttackStyle implements AttackStyle {
    @Override
    public void attack(GUnit attacker, GObject aim) {
        if (!GameModel.MODEL.isNear(attacker, aim)) {
            GameModel.MODEL.error("errorText", "AimIsTooFar");
            return;
        }
        Hit hit = Hit.createHit(attacker, aim);
        final List<GMod> mods = attacker.getMods();
        for (GMod mod : mods) {
            mod.onHit(hit);
        }
        GameModel.MODEL.log("base", "Hits", attacker, aim);
        aim.takeHit(hit);
        GameModel.MODEL.endTurn();
    }
}
