package sample.Events;

import sample.Core.GEvent;
import sample.Core.GUnit;
import sample.Core.GameModel;
import sample.Core.Hit;

/**
 * Created by kondrashov on 26.08.2016.
 */
public class UnitDeathEvent extends GEvent {
    private final GUnit unit;
    private Hit hit;

    @Override
    protected void logAfterEvent() {
        GameModel.MODEL.log("base", "Dies", unit);
    }

    public UnitDeathEvent(GUnit unit) {
        this.unit = unit;
    }

    @Override
    protected void perform() {
        unit.die(hit);
    }

    public void setHit(Hit hit) {
        this.hit = hit;
    }

    public Hit getHit() {
        return hit;
    }

    public GUnit getUnit() {
        return unit;
    }
}
