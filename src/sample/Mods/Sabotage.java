package sample.Mods;


import sample.Core.AbstractGMod;
import sample.Core.GameModel;
import sample.Tower.Tower;

public class Sabotage extends AbstractGMod {
    @Override
    public boolean blocksTower(Tower tower) {
        GameModel.MODEL.log("mods", "Sabotage", tower);
        return true;
    }
}
