package sample;

import java.util.Arrays;
import java.util.List;

public class Direction {
    private static List<Direction> CLOCKWISE_DIRECTIONS = Arrays.asList(
            new Direction(1, -1),
            new Direction(1, 0),
            new Direction(1, 1),
            new Direction(0, 1),
            new Direction(-1, 1),
            new Direction(-1, 0),
            new Direction(-1, -1),
            new Direction(0, -1)
    );
    private final int x;
    private final int y;

    private Direction(int dx, int dy) {
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Direction) {
            Direction xy = (Direction) o;
            return xy.x == x && xy.y == y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return x * 10000 + y;
    }

    @Override
    public String toString() {
        return String.format("(%s:%s)", x, y);
    }

    public Direction turn(boolean clockwise) {
        int index = CLOCKWISE_DIRECTIONS.indexOf(this);
        int next = clockwise ? ++index : --index;
        if (next < 0) {
            next = CLOCKWISE_DIRECTIONS.size() - 1;
        }
        if (next >= CLOCKWISE_DIRECTIONS.size()) {
            next = 0;
        }

        return CLOCKWISE_DIRECTIONS.get(next);
    }
}
