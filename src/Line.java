public class Line {
    public double m;
    public double c;
    public Point A;
    public Point B;

    public Line(Point a, Point b){
        this.A = a;
        this.B = b;
        double ax = A.x;
        double ay = A.y;
        double bx = B.x;
        double by = B.y;

        this.m = (ay - by)/(ax - bx);
        this.c = (ay - (m * ax));
    }

    @Override
    public String toString() {
        return "( m:" + m + "| c:" + c + ")" + "||" + A + "|" + B + ")";
    }

}