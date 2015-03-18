package sample;


public class GObjectFactory {

    public static GObject create(UnitType type) {
        GObject gObject = null;
        switch (type) {
            case Footman: {
                gObject = new GUnit(4, 21, 2, 2);
                break;
            }
            case Inquisitor:{
                gObject = new GUnit(5, 24, 2, 2);
                break;
            }
            case Archer:{
                gObject = new GUnit(2, 24, 1, 1);
                break;
            }
            case Mage:{
                gObject = new GUnit(2, 24, 0, 2);
                break;
            }
            case Assassin:{
                gObject = new GUnit(4, 31, 1, 2);
                break;
            }
            case Tower:{
                gObject = new Tower();
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
