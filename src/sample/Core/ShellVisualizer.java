package sample.Core;


public interface ShellVisualizer {
    void step(GameCell cell, GameCell nextCell);

    void create(GameCell cell, Shell shell);

    void destroy(GameCell cell);
}
