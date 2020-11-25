public class Vector {

    public int x;
    public int y;
    public float v;
    public float v1;
    public double vx;
    public double v1x;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector(float v, float v1) {
        this.v = v;
        this.v1 = v1;
    }

    public Vector(double v, double v1) {
        this.vx = v;
        this.v1x = v1;
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }

}
