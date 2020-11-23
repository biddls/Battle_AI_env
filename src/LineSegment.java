public class LineSegment {

    public Point A;
    public Point B;
    public Vector dir;
    public int type;

    public LineSegment(Point A,Point B,int type){
        this.A = A;
        this.B = B;
        this.type = type;
        dir = new Vector(B.x-A.x, B.y-A.y);
    }

    public LineSegment(Point A,Vector dir){
        this.A = A;
        this.dir = dir;
        this.B = new Point(A.x + dir.x,A.y + dir.y,1);
    }

    @Override
    public String toString() {
        return "(" + A + " -> " + B + ")";
    }
}
