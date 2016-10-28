package sample.Core;

import sample.Graphics.BoardCell;
import sample.XY;

import java.util.ArrayList;

public class GameCell implements Selectable, PlaceHaving {
    private final ArrayList<CellLink> cellLinks = new ArrayList<>();
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

    public void link(GameCell cell, int length) {
        cellLinks.add(new CellLink(this, cell, length));
    }
}
