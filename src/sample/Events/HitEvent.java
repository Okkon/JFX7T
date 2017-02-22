package sample.Events;

import sample.Core.*;

/**
 * Created by kondrashov on 16.08.2016.
 */
public class HitEvent extends GEvent {
    private Hit hit;

    public HitEvent(Hit hit) {
        this.hit = hit;
    }


    @Override
    protected void logBeforeEvent() {
        GameModel.MODEL.log("base", "Hits", hit.getAttacker(), hit.getAim());
        GameModel.MODEL.log("base", "TakesHit", hit.getAim(), hit.getDamageType(), hit.getDamage());
    }

    @Override
    protected void logAfterEvent() {
        if (hit.getAim() instanceof GUnit) {
            GUnit unit = (GUnit) hit.getAim();
            GameModel.MODEL.log("base", "UnitLoseHp", unit, unit.getHP() + hit.getTakenDamage(), unit.getHP());
        }
    }

    @Override
    protected void perform() {
        GObject aim = hit.getAim();
        if (aim != null) {
            aim.takeHit(hit);
        }
    }

    public Hit getHit() {
        return hit;
    }
}
