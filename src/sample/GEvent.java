package sample;

public abstract class GEvent {

    public void process() {
        logBeforeEvent();
        perform();
        visualize();
        logAfterEvent();
    }

    private void logAfterEvent() {
    }

    protected void visualize() {
    }

    protected void logBeforeEvent() {
    }

    protected abstract void perform();

}
