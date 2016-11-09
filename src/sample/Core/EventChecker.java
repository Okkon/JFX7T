package sample.Core;

/**
 * Created by olko1016 on 10/31/2016.
 */
public interface EventChecker<T extends GEvent> {
    boolean checkEvent(T event);
}
