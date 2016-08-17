package sample.Events;

import sample.Core.*;


public class OwnerChangeEvent extends GEvent {
    private final GObject obj;
    private final Player newOwner;

    public OwnerChangeEvent(GObject obj, Player newOwner) {
        this.obj = obj;
        this.newOwner = newOwner;
    }

    @Override
    protected void visualize() {
        GObjectVisualizer visualizer = obj.getVisualizer();
        visualizer.changeOwner(newOwner);
    }

    @Override
    protected void perform() {
        obj.setPlayer(newOwner);
    }

    @Override
    protected void logBeforeEvent() {
        GameModel.MODEL.log("base", "ChangeOwner", obj, obj.getPlayer(), newOwner);
    }
}
