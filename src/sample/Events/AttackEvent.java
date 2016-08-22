package sample.Events;

import sample.Core.GEvent;
import sample.Core.GUnit;
import sample.Core.PlaceHaving;
import sample.GActions.AttackAction;

public class AttackEvent extends GEvent {
    private GUnit attacker;
    private PlaceHaving aim;
    private AttackAction attackAction;

    @Override
    protected void perform() {
        if (attackAction == null) {
            attackAction = AttackAction.getInstance();
        }
        attackAction.attack(attacker, aim);
    }

    public AttackEvent setAttacker(GUnit attacker) {
        this.attacker = attacker;
        return this;
    }

    public AttackEvent setAim(PlaceHaving aim) {
        this.aim = aim;
        return this;
    }

    public AttackEvent setAttackAction(AttackAction attackAction) {
        this.attackAction = attackAction;
        return this;
    }

    public AttackAction getAttackAction() {
        return attackAction;
    }
}
