package sample;


public class XY {
    public static final int diagonalLength = 15;
    public static final int straightLength = 10;
    private int x;
    private int y;

    public XY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ":" + y;
    }

    public static boolean isNear(XY p1, XY p2) {
        if (p1.equals(p2)) {
            return false;
        }
        return XY.getDistance(p1, p2) <= diagonalLength;
    }

    public static int getDistance(XY p1, XY p2) {
        final int x1 = p1.getX();
        final int y1 = p1.getY();
        final int x2 = p2.getX();
        final int y2 = p2.getY();
        final int dx = Math.abs(x2 - x1);
        final int dy = Math.abs(y2 - y1);
        final int notDiagonal = Math.abs(dx - dy);
        return (Math.max(dx, dy) - notDiagonal) * diagonalLength + notDiagonal * straightLength;
    }
}
