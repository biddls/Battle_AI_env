public class LineSegment {

    public Point A;
    public Point B;
    public int type;
    public Vector dir;
    public double angle;
    public float length;


    public LineSegment(Point A,Point B, int type){
        this.A = A;
        this.B = B;
        this.type = type;
        dir = new Vector(B.x-A.x, B.y-A.y);
        if (type == 0) {//if ray it gets the length of it
            this.length = (float) Math.sqrt(Math.pow(dir.x, 2) +
                    Math.pow(dir.y, 2));
        }
        if (type == 1) {//if a wall it gets the angle of it
            this.angle = Math.atan(A.y - B.y) / (A.x - B.x);
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
