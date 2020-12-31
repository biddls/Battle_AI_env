package FPS;

public class LineSegment3D{
    public Point3D A3D;
    public Point3D B3D;
    public int type;

    public LineSegment3D(Point3D A, Point3D B, int type) {
        this.A3D = A;
        this.B3D = B;
        this.type = type;
    }
}
