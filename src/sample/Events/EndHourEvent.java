package sample.Events;

import sample.Core.GEvent;
import sample.Core.GObject;
import sample.Core.GameModel;

import java.util.HashSet;
import java.util.Set;


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
        Set<GObject> objects = GameModel.MODEL.getObjects();
        Set<GObject> objectsCopy = new HashSet<>(objects);
        objectsCopy.forEach(GObject::onEndHour);
    }

    public int getHour() {
        return hour;
    }
}
