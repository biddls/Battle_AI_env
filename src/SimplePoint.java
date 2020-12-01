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

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }

}
