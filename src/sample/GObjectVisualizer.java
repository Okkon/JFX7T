package sample;


public interface GObjectVisualizer extends Visualizer {
    void changePlace(GameCell currentCell, GameCell cellToGo);

    void die(GameCell place);

    void setPlayer(Player player);

    void setReady(boolean isReady);

    void create(GameCell cell);

    void setSelected(boolean b);

    void changeHP(int hp);

    void startAttack(GObject aim);

    void applyEffect(String effect);

    void setPanel(GamePanel gamePanel);
}
