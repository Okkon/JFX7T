package sample;

public class GameCell implements Selectable {
    private XY xy;
    private GObject obj;
    private CellVisualizer cellVisualizer;

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
    public void select() {
    }

    @Override
    public void showSelectionPossibility() {
        cellVisualizer.showSelectionPossibility();
    }

    @Override
    public void hideSelectionPossibility() {
        cellVisualizer.hideSelectionPossibility();
    }

    @Override
    public String toString() {
        return xy.getX() + ":" + xy.getY();
    }

    public void setCellVisualizer(CellVisualizer cellVisualizer) {
        this.cellVisualizer = cellVisualizer;
    }

    public boolean isNotEmpty() {
        return getObj() != null;
    }

    public CellVisualizer getVisualizer() {
        return cellVisualizer;
    }
}
