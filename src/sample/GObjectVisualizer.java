package sample;

/**
 * Created by kondrashov on 19.03.2015.
 */
public interface GObjectVisualizer extends Visualizer {
    void changePlace(GameCell currentCell, GameCell cellToGo);

    void die(GameCell place);

    void setPlayer(Player player);

    void setReady(boolean isReady);

    void create(GameCell cell);

    void setSelected(boolean b);

    void changeHP(int hp);
}
