package sample;


import java.util.ArrayList;
import java.util.List;

public final class XY {
    public static final int diagonalLength = 15;
    public static final int straightLength = 10;
    private final int x;
    private final int y;

    public static XY get(int x, int y) {
        return new XY(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof XY) {
            XY xy = (XY) o;
            return xy.x == x && xy.y == y;
        }
        return false;
    }

    private XY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return x * 10000 + y;
    }

    @Override
    public String toString() {
        return x + ":" + y;
    }

    public static boolean isNear(XY p1, XY p2) {
        return !p1.equals(p2) && XY.getDistance(p1, p2) <= diagonalLength;
    }

    public boolean isNear(XY p1) {
        return !p1.equals(this) && XY.getDistance(p1, this) <= diagonalLength;
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

    public static boolean isOnOneLine(XY p1, XY p2) {
        final int x1 = p1.getX();
        final int y1 = p1.getY();
        final int x2 = p2.getX();
        final int y2 = p2.getY();
        if (x1 == x2 || y1 == y2) {
            return true;
        }
        final int dx = Math.abs(x2 - x1);
        final int dy = Math.abs(y2 - y1);
        return dx == dy;
    }

    public XY step(Direction direction) {
        return new XY(x + direction.getX(), y + direction.getY());
    }

    public static XY step(XY currentPlace, Direction direction) {
        return new XY(currentPlace.x + direction.getX(), currentPlace.y + direction.getY());
    }

    public List<XY> getPlaces(XY startPlace, boolean clockwise, int times) {
        List<XY> result = new ArrayList<>();
        result.add(startPlace);
        Direction direction = Direction.findDirection(this, result.get(result.size() - 1));
        for (int i = 0; i < times; i++) {
            direction = direction.turn(clockwise);
            result.add(this.step(direction));
        }
        return result;
    }
}
