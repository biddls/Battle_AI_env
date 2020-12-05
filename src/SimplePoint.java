public class SimplePoint {

    public double x;
    public double y;

    public SimplePoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public SimplePoint(Point a){
        this.x = a.x + a.xd;
        this.y = a.y + a.yd;
    }

    public static SimplePoint add(SimplePoint a, SimplePoint b){
        return new SimplePoint(a.x + b.x, a.y + b.y);
    }

    public static SimplePoint subtractPosVector(SimplePoint a, SimplePoint b){
        return new SimplePoint(Math.abs(a.x - b.x), Math.abs(a.y - b.y));
    }

    public static SimplePoint subtract(SimplePoint a, SimplePoint b){
        return new SimplePoint(a.x - b.x, - a.y - - b.y);
    }

    public static SimplePoint subtractPosVector(double x, double y, SimplePoint b){
        return new SimplePoint(Math.abs(x - b.x), Math.abs(y - b.y));
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }
}