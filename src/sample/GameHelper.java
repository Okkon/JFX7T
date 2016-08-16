package sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by kondrashov on 16.08.2016.
 */
public class GameHelper {
    public static GUnit getRandom(Collection<GUnit> collection) {
        if (collection.isEmpty()) {
            return null;
        }
        ArrayList<GUnit> list = new ArrayList(collection);
        Collections.shuffle(list);
        return list.iterator().next();
    }
}
