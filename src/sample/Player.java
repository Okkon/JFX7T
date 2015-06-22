package sample;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public static final Player NEUTRAL = new Player("Neutral", Color.GREY);
    private String name;
    private int score;
    private Color color;
    private List<GUnit> availableUnits = new ArrayList<GUnit>();

    @Override
    public String toString() {
        return name + String.format("(%d)", score);
    }

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        score = 0;
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

    public boolean isEnemyFor(Player player) {
        return !equals(player);
    }

    public boolean isEnemyFor(GObject object) {
        return equals(object.getPlayer());
    }

    public boolean isOwnerFor(GObject object) {
        return object.getPlayer().equals(this);
    }

    public List<GUnit> getAvailableUnits() {
        return availableUnits;
    }

    public void score(int point) {
        score += point;
    }

    public String getName() {
        return name;
    }
}
