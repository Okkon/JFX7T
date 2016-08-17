package sample.Helpers;

import sample.Core.GameModel;
import sample.MyConst;

import java.util.ResourceBundle;

public class NameHelper {
    public static String getName(String packageName, String key, Object... objects) {
        try {
            return ResourceBundle.getBundle(MyConst.RESOURCE_BUNDLE_LOCATION + packageName).getString(key);
        } catch (Exception e) {
            GameModel.MODEL.log("base", "NameNotFound", e.getMessage(), objects);
            return key;
        }
    }

}
