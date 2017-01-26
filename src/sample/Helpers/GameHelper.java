package sample.Helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by kondrashov on 16.08.2016.
 */
public class GameHelper {
    public static <T> T getRandomFromCollection(Collection<T> collection) {
        if (collection.isEmpty()) {
            return null;
        }
        ArrayList<T> list = new ArrayList(collection);
        Collections.shuffle(list);
        return list.iterator().next();
    }
}
