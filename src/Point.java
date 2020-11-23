/**
 * Created by Armin on 10/19/2017.
 */

public class Point {

    public float x;
    public float y;
    public int type;

    public Point(float x, float y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + "|" + type + ")";
    }

}


