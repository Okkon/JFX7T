package sample.Events.Listeners;

import sample.Core.GEventListener;
import sample.Core.GObject;
import sample.Core.GameModel;
import sample.Core.Player;
import sample.Events.EndHourEvent;
import sample.Events.ScoreChangeEvent;
import sample.Filters.FilterFactory;
import sample.Tower.Tower;

import java.util.List;


public class ScoreForTowers<T extends EndHourEvent> extends GEventListener<T> {
    @Override
    public void doAfterEvent(EndHourEvent event) {
        final int hour = event.getHour();
        if (hour % 3 == 0) {
            List<GObject> objects = GameModel.MODEL.getObjects(FilterFactory.ClassFilter.newInstance().setClass(Tower.class));
            for (GObject object : objects) {
                Tower tower = ((Tower) object);
                final Player owner = tower.getPlayer();
                if (owner != null && !owner.equals(Player.NEUTRAL)) {
                    new ScoreChangeEvent(owner, hour / 3, tower.getPlace()).process();
                }
            }
        }

    }
}
