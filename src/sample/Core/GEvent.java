package sample.Core;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GEvent {
    private static final Map<Class, List<GEventListener<GEvent>>> listenersMap = new HashMap<>();
    protected GameModel model = GameModel.MODEL;

    public final void process() {
        doBeforeEvent();
        if (!canBePerformed()) {
            return;
        }
        visualize();
        perform();
        doAfterEvent();
    }

    private boolean canBePerformed() {
        return true;
    }

    protected void doBeforeEvent() {
        logBeforeEvent();
        List<GEventListener<GEvent>> listenerList = listenersMap.get(getClass());
        if (listenerList != null) {
            for (GEventListener<GEvent> listener : listenerList) {
                listener.doBeforeEvent(this);
            }
        }
    }

    protected void doAfterEvent() {
        List<GEventListener<GEvent>> listenerList = listenersMap.get(getClass());
        if (listenerList != null) {
            for (GEventListener<GEvent> listener : listenerList) {
                listener.doAfterEvent(this);
            }
        }
        logAfterEvent();
    }

    protected void logAfterEvent() {
    }

    protected void visualize() {
    }

    protected void logBeforeEvent() {
    }

    protected abstract void perform();

    public static void addListener(Class<? extends GEvent> eventClass, GEventListener listener) {
        List<GEventListener<GEvent>> listenerList = listenersMap.get(eventClass);
        if (listenerList == null) {
            listenerList = new ArrayList<>();
            listenerList.add(listener);
            listenersMap.put(eventClass, listenerList);
        } else {
            listenerList.add(listener);
        }
    }


    public static void removeListener(Class<? extends GEvent> eventClass, GEventListener listener) {
        List<GEventListener<GEvent>> listenerList = listenersMap.get(eventClass);
        if (listenerList != null) {
            listenerList.remove(listener);
        }
    }
}
