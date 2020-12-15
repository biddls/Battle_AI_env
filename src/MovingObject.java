import java.util.ArrayList;

public class MovingObject {
    public float positionX;
    public float positionY;
    public float direction = 0;//degrees
    public int size;
    public int health;
    public int type;
    public final int[] DIMENSIONS = {10 + (size / 2), 10 + (size / 2), 640 - (this.size / 2), 360 - (size / 2)};
    private int speed;

    //i know these 2 could be more efficient, its just nicer this way
    public static boolean BetweenX(Point intersection, LineSegment segment){
        SimplePoint inter = intersection.getPoint();
        int min = Math.min(segment.A.x, segment.B.x);
        int max = Math.max(segment.A.x, segment.B.x);
        if(min <= inter.x && inter.x <= max){
            return true;
        }
        return false;
    }

    public static boolean BetweenY(Point intersection, LineSegment segment){
        SimplePoint inter = intersection.getPoint();
        int min = Math.min(segment.A.y, segment.B.y);
        int max = Math.max(segment.A.y, segment.B.y);
        if(min <= inter.y && inter.y <= max){
            return true;
        }
        return false;
    }
}
