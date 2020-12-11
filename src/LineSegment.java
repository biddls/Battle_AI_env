public class LineSegment {

    public Point A;
    public Point B;
    public SimplePoint a;
    public SimplePoint b;
    public int type;
    public Vector dir;
    public double angleDeg;
    public double angleRad;
    public double angleGrad;
    public float length;
    public float c;

    public LineSegment(Point A,Point B, int type){
        this.A = A;
        this.B = B;
        this.a = A.getPoint();
        this.b = B.getPoint();
        this.type = type;
        dir = new Vector(b.x-a.x, b.y-a.y);
        if (type == 0) {//if ray it gets the length of it
            this.length = (float) Math.sqrt(Math.pow(dir.x, 2) +
                    Math.pow(dir.y, 2));
        }
        if (type == 1 && a.x - b.x != 0) {//if a wall it gets the angle of it
            this.angleRad = Math.atan((a.y - b.y) / (a.x - b.x));
            this.angleDeg = Math.toDegrees(angleRad);
            this.angleGrad = (a.y - b.y) / (a.x - b.x);
            this.c = (float) (a.y - angleGrad * a.x);
        }
    }

    public LineSegment(Point A,Vector dir){
        this.A = A;
        this.dir = dir;
        this.B = new Point(A.x + dir.x,A.y + dir.y);
    }

    @Override
    public String toString() {
        return "(" + A + " -> " + B + "||" + type + ")";
    }
}