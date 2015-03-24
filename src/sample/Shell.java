package sample;

public class Shell {
    protected GameCell cell;
    protected Direction direction;
    protected int minDamage;
    protected int maxDamage;
    protected int coveredDistance;
    protected int maxDistance;
    protected GUnit attacker;
    protected String name;
    private boolean stopable;

    public void fire() {
        step();
    }

    private void step() {
        final GameCell nextCell = GameModel.MODEL.getNextCell(cell, direction);
        int stepPrice = direction.isDiagonal() ? XY.diagonalLength : XY.straightLength;
        if (nextCell != null && coveredDistance + stepPrice < maxDistance) {
            coveredDistance += stepPrice;
            cell = nextCell;
            final GObject obj = cell.getObj();
            if (obj != null) {
                bumpInto(obj);
                if (stopable) return;
            }
        }
    }

    private void bumpInto(GObject obj) {
        final Hit hit = Hit.createHit(attacker, obj, minDamage, maxDamage);
        obj.takeHit(hit);
    }


}
