package market.util;

public class Point {

    public int x;
    public double y;

    public Point() {
        this(0, 0D);
    }

    public Point(Point point) {
        this(point.x, point.y);
    }

    public Point(int x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

}
