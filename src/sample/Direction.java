package sample;

public class Direction {
    private int x;
    private int y;

    public boolean isDiagonal() {
        return x != 0 && y != 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
