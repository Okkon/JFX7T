package sample;

import java.util.List;

/**
 * Created by kondrashov on 19.03.2015.
 */
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
        for (GMod mod : aim.getMods()) {
            mod.onTakeHit(hit);
        }
        GameModel.MODEL.log(attacker + " hit " + aim + " with power = " + hit.getDamage());
        aim.takeHit(hit);
        GameModel.MODEL.endTurn();
    }
}
