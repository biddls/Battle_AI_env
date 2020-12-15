import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by Armin on 10/19/2017.
 */

public class Point {

    public int x;
    public int y;
    public IntStream yS;
    public IntStream xS;
    public double xd;
    public double yd;
    public int type;
    public float direction;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y) {
        this.xd = x;
        this.yd = y;
    }

    public Point(int x, int y, float direction ) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }


    public Point(int x, int y, int type ) {
        this.x = x;
        this.y = y;
        this.type = type;

    }

    public Point(int x, IntStream yS) {
        this.x = x;
        this.yS = yS;
    }

    public Point(IntStream xS, int y) {
        this.xS = xS;
        this.y = y;
    }

    public SimplePoint getPoint(){
        return new SimplePoint(x + xd, y + yd);
    }

    @Override
    public String toString() {
        return "(" + (double) (x + xd) + "|" + (double) (y + yd) + ")" + "||" + type + "|" + direction + ")";
    }

}


