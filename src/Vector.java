public class Vector {

    public int x;
    public int y;
    public float v;
    public float v1;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector(float v, float v1) {
        this.v = v;
        this.v1 = v1;
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }

}
