package sample;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;


public class PropertyHelper {
    private static final String propertyFilePlace = "config.properties";
    private static Properties prop;

    public static void createSettings() {
        prop = new Properties();
        try (OutputStream output = new FileOutputStream(propertyFilePlace)) {
            prop.setProperty("locale", "en");
            prop.setProperty("turnsInGame", "9");
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void loadSettings() {
        prop = new Properties();
        try (FileInputStream input = new FileInputStream(propertyFilePlace)) {
            prop.load(input);
            /*System.out.println(prop.getProperty("locale"));
            System.out.println(prop.getProperty("turnsInGame"));*/
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public Object getProperty(String propertyName) {
        return prop.getProperty(propertyName);
    }
}
