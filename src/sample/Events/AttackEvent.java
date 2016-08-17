package sample.Events;

import sample.Core.GEvent;
import sample.Core.GObject;
import sample.Core.GUnit;
import sample.GActions.AttackAction;

public class AttackEvent extends GEvent {
    private GUnit attacker;
    private GObject aim;
    private AttackAction attackAction;

    @Override
    protected void perform() {
        if (attackAction == null) {
            attackAction = AttackAction.DEFAULT;
        }
        attackAction.attack(attacker, aim);
    }

    public AttackEvent setAttacker(GUnit attacker) {
        this.attacker = attacker;
        return this;
    }

    public AttackEvent setAim(GObject aim) {
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
