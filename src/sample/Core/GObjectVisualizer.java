package sample.Core;


import javafx.scene.image.Image;
import sample.Graphics.GamePanel;

public interface GObjectVisualizer extends Visualizer {
    void changePlace(GameCell currentCell, GameCell cellToGo);

    void die(GameCell place);

    void setPlayer(Player player);

    void setReady(boolean isReady);

    void create(GameCell cell);

    void setSelected(boolean b);

    void changeHP(int hp);

    void startAttack(Hit hit);

    void applyEffect(GMod mod);

    void setPanel(GamePanel gamePanel);

    Image getImage();

    void changeOwner(Player newOwner);
}
