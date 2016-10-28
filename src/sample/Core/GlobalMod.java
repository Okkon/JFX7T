package sample.Core;

import java.util.ArrayList;
import java.util.List;


public abstract class GlobalMod<T extends GEvent, H extends GObject> extends GEventListener<T> {
    private List<H> holderList = new ArrayList<>();
    protected Class<? extends GEvent> eventClass;

    public void register(H obj) {
        if (holderList.isEmpty()) {
            GEvent.addListener(eventClass, this);
        }
        holderList.add(obj);
    }

    protected List<H> getHolders() {
        return holderList;
    }

    public void unregister(H obj) {
        holderList.remove(obj);
        if (holderList.isEmpty()) {
            GEvent.removeListener(eventClass, this);
        }
    }
}
