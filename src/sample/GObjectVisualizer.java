package sample;

/**
 * Created by kondrashov on 19.03.2015.
 */
public interface GObjectVisualizer {
    void changePlace(GameCell currentCell, GameCell cellToGo);

    void die(GameCell place);

    void setPlayer(Player player);

    void setReady(boolean isReady);

    void create(GameCell cell);
}
