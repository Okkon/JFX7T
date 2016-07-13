package sample.Events;

import sample.*;

import java.util.List;

public class AttackEvent extends GEvent {
    private GUnit attacker;
    private GObject aim;

    @Override
    protected void perform() {
        Hit hit = Hit.createHit(attacker, aim);
        final List<GMod> mods = attacker.getMods();
        for (GMod mod : mods) {
            mod.onHit(hit);
        }
        attacker.getVisualizer().startAttack(hit);
        GameModel.MODEL.log("base", "Hits", attacker, aim);
        aim.takeHit(hit);

    }

    public AttackEvent setAttacker(GUnit attacker) {
        this.attacker = attacker;
        return this;
    }

    public AttackEvent setAim(GObject aim) {
        this.aim = aim;
        return this;
    }
}
