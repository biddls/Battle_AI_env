public class SimpleVector {
    public double x;
    public double y;

    public SimpleVector(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }
}
