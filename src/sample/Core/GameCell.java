package sample.Core;

import sample.Graphics.BoardCell;
import sample.XY;

public class GameCell implements Selectable, PlaceHaving {
    private XY xy;
    private GObject obj;
    private BoardCell visualizer;

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

    public void setVisualizer(BoardCell visualizer) {
        this.visualizer = visualizer;
    }

    public boolean isNotEmpty() {
        return getObj() != null;
    }

    public BoardCell getVisualizer() {
        return visualizer;
    }
}
