package sample;

/**
 * Created by kondrashov on 19.03.2015.
 */
public class BaseAttackType implements AttackType {
    @Override
    public void attack(GUnit attacker, GObject aim) {
        Hit hit = Hit.createHit(attacker, aim);
        GameModel.MODEL.log(attacker + " hit " + aim + " with power = " + hit.getDamage());
        aim.takeHit(hit);
        GameModel.MODEL.endTurn();
    }
}
