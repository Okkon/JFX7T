package sample;

import java.util.List;

public class BaseAttackType implements AttackType {
    @Override
    public void attack(GUnit attacker, GObject aim) {
        if (!GameModel.MODEL.isNear(attacker, aim)) {
            GameModel.MODEL.error("aim is too far!");
            return;
        }
        Hit hit = Hit.createHit(attacker, aim);
        final List<GMod> mods = attacker.getMods();
        for (GMod mod : mods) {
            mod.onHit(hit);
        }
        GameModel.MODEL.log(attacker + " hits " + aim);
        aim.takeHit(hit);
        GameModel.MODEL.endTurn();
    }
}
