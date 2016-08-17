package sample;


import sample.Core.GObject;
import sample.Core.ObjectType;
import sample.Tower.MainTower;
import sample.Tower.Tower;

public class GObjectFactory {

    public static GObject create(ObjectType type) {
        GObject gObject = null;
        switch (type) {
            case Tower: {
                gObject = new Tower();
                break;
            }
            case MainTower: {
                gObject = new MainTower();
                break;
            }
        }
        return gObject;
    }
}
