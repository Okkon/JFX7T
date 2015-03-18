package sample;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kondrashov on 25.02.2015.
 */
public class Player {
    public static final Player NEUTRAL = new Player("Neutral", Color.GREY);
    private String name;
    private int score;
    private Color color;
    private Object activeUnits;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public List<GUnit> getUnits() {
        List<GUnit> units = new ArrayList<GUnit>();
        for (GObject object : GameModel.MODEL.getObjects()) {
            if (object instanceof GUnit) {
                GUnit gUnit = (GUnit) object;
                if (gUnit.getPlayer().equals(this)) {
                    units.add(gUnit);
                }
            }
        }
        return units;
    }

    public List<GUnit> getActiveUnits() {
        List<GUnit> units = new ArrayList<GUnit>();
        for (GUnit unit : getUnits()) {
            if (unit.canAct()) {
                units.add(unit);
            }
        }
        return units;
    }
}
