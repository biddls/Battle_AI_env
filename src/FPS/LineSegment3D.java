package FPS;

public class LineSegment3D{
    public Point3D A3D;
    public Point3D B3D;
    public int type;
    public int distance;

    public LineSegment3D(Point3D A, Point3D B, int type, int distance) {
        this.A3D = A;
        this.B3D = B;
        this.type = type;
        this.distance = distance;
    }
}
