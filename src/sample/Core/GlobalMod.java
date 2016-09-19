package sample.Core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kondrashov on 16.09.2016.
 */
public abstract class GlobalMod<T extends GEvent, H extends GObject> extends GEventListener<T> {
    private List<H> holderList = new ArrayList<>();
    protected Class<? extends GEvent> eventClass;

    public void register(H obj) {
        if (holderList.isEmpty()) {
            GEvent.addListener(eventClass, this);
        }
        holderList.add(obj);
    }

    public List<H> getHolders() {
        return holderList;
    }

    public void unregister(H obj) {
        holderList.remove(obj);
        if (holderList.isEmpty()) {
            GEvent.removeListener(eventClass, this);
        }
    }
}