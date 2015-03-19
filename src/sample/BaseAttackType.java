package sample;

/**
 * Created by kondrashov on 19.03.2015.
 */
public class BaseAttackType implements AttackType {
    @Override
    public void attack(GUnit attacker, GObject aim) {
        if (!GameModel.MODEL.isNear(attacker, aim)) {
            GameModel.MODEL.error("aim is too far!");
        }
        Hit hit = Hit.createHit(attacker, aim);
        GameModel.MODEL.log(attacker + " hit " + aim + " with power = " + hit.getDamage());
        aim.takeHit(hit);
        GameModel.MODEL.endTurn();
    }
}
