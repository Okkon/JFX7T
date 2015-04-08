package sample;

import java.util.List;
import java.util.ResourceBundle;

public class BaseAttackStyle implements AttackStyle {
    @Override
    public void attack(GUnit attacker, GObject aim) {
        if (!GameModel.MODEL.isNear(attacker, aim)) {
            GameModel.MODEL.error(ResourceBundle.getBundle("sample/errorText").getString("AimIsTooFar"));
            return;
        }
        Hit hit = Hit.createHit(attacker, aim);
        final List<GMod> mods = attacker.getMods();
        for (GMod mod : mods) {
            mod.onHit(hit);
        }
        GameModel.MODEL.log(attacker + ResourceBundle.getBundle("sample/base").getString("Hits") + aim);
        aim.takeHit(hit);
        GameModel.MODEL.endTurn();
    }
}
