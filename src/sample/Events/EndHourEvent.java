package sample.Events;

import sample.Core.GEvent;
import sample.Core.GObject;
import sample.Core.GameModel;


public class EndHourEvent extends GEvent {
    private int hour;

    public EndHourEvent(int hour) {
        this.hour = hour;
    }

    @Override
    protected void logBeforeEvent() {
        GameModel.MODEL.log("base", "HourEnds", hour);
    }

    @Override
    protected void perform() {
        GameModel.MODEL.getObjects().forEach(GObject::onEndHour);
    }

    public int getHour() {
        return hour;
    }
}
