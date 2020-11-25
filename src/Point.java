import java.util.Optional;

/**
 * Created by Armin on 10/19/2017.
 */

public class Point {

    public int x;
    public int y;
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
        System.out.println(this.direction);
    }


    public Point(int x, int y, int type ) {
        this.x = x;
        this.y = y;
        this.type = type;

    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")" + "||" + type + "|" + direction + ")";
    }

}


