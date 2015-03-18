package sample;

public class GameCell implements Selectable{
    private XY xy;
    private GObject obj;
    private CellVisualizer graphics;

    public GameCell(XY xy) {
        this.xy = xy;
    }

    public GObject getObj() {
        return obj;
    }

    public void setObj(GObject obj) {
        this.obj = obj;
    }

    public XY getXy() {
        return xy;
    }

    @Override
    public void select(GAction action) {
        final GObject object = getObj();
        if (object != null && action.canSelect(object)) {
            object.select(action);
        }
    }

    @Override
    public String toString() {
        return xy.getX() + ":" + xy.getY();
    }

    public void setGraphics(CellVisualizer graphics) {
        this.graphics = graphics;
    }
}
