package FPS;

import RayCastCore.Point;

public class Point3D extends Point{
    int distance;

    public Point3D(Point p) {
        super(p.x, p.y);
    }

    public Point3D(double x, double y) {
        super(x, y);
    }

    public Point3D(double x, double y, int type) {
        super(x, y, type);
    }

    public Point3D(int x, int y, float direction) {
        super(x, y, direction);
    }
    @Override
    public String toString() {
        return "(" + x + "|" + y + ")" + "||" + type + "|" + distance + ")";
    }
}