package sample.Core;


public abstract class GEventListener<T extends GEvent> {
    public void doBeforeEvent(T event) {
    }

    ;

    public void doAfterEvent(T event) {
    }

    ;
}
