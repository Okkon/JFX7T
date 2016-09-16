package sample.Mods;


import sample.Core.AbstractGMod;
import sample.Core.GameModel;
import sample.Tower.Tower;

public class Sabotage extends AbstractGMod {
    private static final Sabotage INSTANCE = new Sabotage();

    private Sabotage() {
    }

    public static Sabotage getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean blocksTower(Tower tower) {
        GameModel.MODEL.log("mods", "Sabotage", tower);
        return true;
    }
}
