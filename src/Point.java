import java.util.Optional;

/**
 * Created by Armin on 10/19/2017.
 */

public class Point {

    public int x;
    public int y;
    public int type;
    public float dir;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;

    }
    public Point(float dir) {
        this.dir= dir;
    }
    public Point(int x, int y, int type ) {
        this.x = x;
        this.y = y;
        this.type = type;

    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")" + "||" + type + "|" + dir;
    }

}


