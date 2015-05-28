package sample.Mods;


import sample.AbstractGMod;
import sample.GameModel;
import sample.Tower;

public class Sabotage extends AbstractGMod {
    @Override
    public boolean blocksTower(Tower tower) {
        GameModel.MODEL.log("mods", "Sabotage", tower);
        return true;
    }
}
