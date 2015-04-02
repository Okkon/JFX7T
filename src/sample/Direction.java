package sample;

public class Direction {
    private int x;
    private int y;

    public Direction(int dx, int dy) {
        x = dx;
        y = dy;
    }

    public boolean isDiagonal() {
        return x != 0 && y != 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Direction findDirection(XY from, XY to) {
        int x1 = from.getX();
        int y1 = from.getY();
        int x2 = to.getX();
        int y2 = to.getY();
        int dx = sign(x2 - x1);
        int dy = sign(y2 - y1);
        return new Direction(dx, dy);
    }

    private static int sign(int i) {
        if (i == 0) return 0;
        return i > 0 ? 1 : -1;
    }

    public double toAngle() {
        if (x < 0) {
            if (y > 0) {
                return 315d;
            }
            if (y == 0) {
                return 270d;
            }
            if (y < 0) {
                return 225d;
            }
        }
        if (x > 0) {
            if (y > 0) {
                return 45;
            }
            if (y == 0) {
                return 90d;
            }
            if (y < 0) {
                return 135d;
            }
        }
        if (x == 0) {
            if (y > 0) {
                return 0d;
            }
            if (y < 0) {
                return 180d;
            }
            if (y == 0) {
                return -1;
            }
        }
        return -1;
    }
}
