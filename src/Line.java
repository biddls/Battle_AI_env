public class Line {
    public double m;
    public double c;
    public SimplePoint A;
    public SimplePoint B;

    public Line(Point a, Point b){
        this.A = new SimplePoint(a);
        this.B = new SimplePoint(b);
        double ax = A.x;
        double ay = A.y;
        double bx = B.x;
        double by = B.y;

        this.m = (ay - by)/(ax - bx);
        this.c = (ay - (m * ax));
    }
    public SimplePoint evaluateX(double x){
        return new SimplePoint(x, m * x + c);
    }

    public SimplePoint evaluateY(double y){
        return new SimplePoint((y - c)/m, y);
    }

    @Override
    public String toString() {
        return "( m:" + m + "| c:" + c + ")" + "||" + A + "|" + B + ")";
    }

}