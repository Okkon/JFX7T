package sample.Events;

import sample.Core.GEvent;
import sample.Core.GObject;
import sample.Core.GameModel;
import sample.Core.Hit;

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
        GameModel.MODEL.log("base", "TakesHit", this, hit.getDamageType(), hit.getDamage());
    }

    @Override
    protected void perform() {
        GObject aim = hit.getAim();
        if (aim != null) {
            aim.takeHit(hit);
        }
    }
}
