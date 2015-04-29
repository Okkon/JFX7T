package sample;


import sample.GActions.Crossbow;
import sample.GActions.Fireball;
import sample.GActions.HolyShieldSkill;
import sample.Mods.*;

public class GObjectFactory {

    public static GObject create(UnitType type) {
        GObject gObject = null;
        switch (type) {
            case Footman: {
                gObject = new GUnit(4, 21, 2, 2);
                gObject.addMod(new Armor(2));
                gObject.addMod(new Shield(2));
                break;
            }
            case Inquisitor: {
                gObject = new GUnit(5, 25, 2, 2);
                gObject.addMod(new MagicSword());
                gObject.addMod(new MagicArmor(2));
                gObject.addSkill(new HolyShieldSkill());
                break;
            }
            case Archer: {
                gObject = new GUnit(2, 21, 1, 1);
                gObject.addSkill(new Crossbow(2, 2, 60));
                break;
            }
            case Mage: {
                gObject = new GUnit(2, 25, 0, 2);
                gObject.addSkill(new Fireball(1, 2, 60));
                break;
            }
            case Assassin: {
                gObject = new GUnit(4, 31, 1, 2);
                gObject.addMod(new Masking());
                gObject.addMod(new Sabotage());
                break;
            }
            case AstralArcher: {
                gObject = new GUnit(5, 26, 2, 2);
                gObject.addMod(new MagicArmor(2));
                break;
            }
            case Troll: {
                gObject = new GUnit(6, 21, 2, 3);
                gObject.addMod(new Regeneration(3));
                break;
            }
            case Tower: {
                gObject = new Tower();
                break;
            }
            case MainTower: {
                gObject = new MainTower();
                break;
            }
        }
        if (gObject instanceof GUnit) {
            GUnit object = (GUnit) gObject;
            object.setType(type);
        }
        return gObject;
    }
}
