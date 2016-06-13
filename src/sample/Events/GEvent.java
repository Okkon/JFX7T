package sample.Events;


public abstract class GEvent {
    public static GEvent lastEvent;

    private GEvent predecessor;

    public void process() {
        doBeforeEvent();
        visualize();
        perform();
        lastEvent = this;
        doAfterEvent();
    }

    private void doBeforeEvent() {
        logBeforeEvent();
//        GameModel.MODEL.beforeEvent(this);
    }

    private void doAfterEvent() {
        logAfterEvent();
//        GameModel.MODEL.afterEvent(this);
    }

    private void logAfterEvent() {
    }

    protected void visualize() {
    }

    protected void logBeforeEvent() {
    }

    protected abstract void perform();

    public void setPredecessor(GEvent predecessor) {
        this.predecessor = predecessor;
    }

    public GEvent getPredecessor() {
        return predecessor;
    }
}
