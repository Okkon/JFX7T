package sample.Core;


public class CellLink {
    private final GameCell fromCell;
    private final GameCell toCell;
    private final int length;

    public CellLink(GameCell fromCell, GameCell toCell, int length) {
        this.fromCell = fromCell;
        this.toCell = toCell;
        this.length = length;
    }
}
