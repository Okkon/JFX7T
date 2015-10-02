package sample;


public abstract class GEvent {

    public void process() {
        doBeforeEvent();
        visualize();
        perform();
        doAfterEvent();
    }

    private void doBeforeEvent() {
        logBeforeEvent();
        GameModel.MODEL.beforeEvent(this);
    }

    private void doAfterEvent() {
        logAfterEvent();
        GameModel.MODEL.afterEvent(this);
    }

    private void logAfterEvent() {
    }

    protected void visualize() {
    }

    protected void logBeforeEvent() {
    }

    protected abstract void perform();

}
