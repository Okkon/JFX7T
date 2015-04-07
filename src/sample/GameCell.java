package sample;

public class GameCell implements Selectable {
    private XY xy;
    private GObject obj;
    private Visualizer visualizer;

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
    public String toString() {
        return xy.getX() + ":" + xy.getY();
    }

    public void setVisualizer(Visualizer visualizer) {
        this.visualizer = visualizer;
    }

    public boolean isNotEmpty() {
        return getObj() != null;
    }

    public Visualizer getVisualizer() {
        return visualizer;
    }
}
