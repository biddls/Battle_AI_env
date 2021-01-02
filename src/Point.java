import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by Armin on 10/19/2017.
 */

public class Point {

    public double x;
    public double y;

    public int type;
    public float direction;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Point(int x, int y, float direction ) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")" + "||" + type + "|" + direction + ")";
    }

    public static Point subtract(Point a, Point b){
        return new Point(a.x - b.x, - a.y - - b.y);
    }


}


