package sample;

import javafx.scene.image.Image;

public class ImageHelper {
    public static Image getImage(GUnit unit) {
        final String imagePath = String.format("file:res/img/units/%s.bmp", unit.getType().toString().toLowerCase());
        return new Image(imagePath);
    }

    public static Image getImage(Tower tower) {
        final String imagePath = String.format("file:res/img/units/%s.jpg", "tower");
        return new Image(imagePath);
    }
}
