package sample;

import javafx.scene.image.Image;

public class ImageHelper {
    public static Image getImage(GUnit unit) {
        final String imagePath = String.format("file:res/img/units/%s.bmp", unit.getType().toString().toLowerCase());
        return new Image(imagePath);
    }

    public static Image getImage(Tower tower, boolean isMain) {
        final String imagePath = String.format("file:res/img/units/%s.jpg", isMain ? "maintower" : "tower");
        return new Image(imagePath);
    }

    public static Image getPlayerImage(String playerName) {
        final String imagePath = String.format("file:res/img/players/%s.jpg", playerName);
        return new Image(imagePath);
    }
}
