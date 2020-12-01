public class SimplePoint {

    public double x;
    public double y;

    public SimplePoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }

}
