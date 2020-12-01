public class Vector {

    public int x;
    public int y;
    public float vx;
    public float vy;
    public double v1x;
    public double v1y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector(float x, float y) {
        this.vx = x;
        this.vy = y;
    }

    public Vector(double x, double y) {
        this.v1x = x;
        this.v1y = y;
    }

    public SimpleVector getVector(){
        return new SimpleVector(x + vx + v1y, y + vy + v1y);
    }

    @Override
    public String toString() {
        return "(" + x + vx + v1y + "|" + y + vy + v1y + ")";
    }

}
