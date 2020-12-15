import java.util.ArrayList;

public class Bullet extends MovingObject{

    public float positionX;
    public float positionY;
    public float direction;//degrees
    private double cos;//cos
    private double sin;//sin
    public int size = 6;//gotta be even
    public int health = 1;//gotta be even
    private final int[] DIMENSIONS = {10 + (size / 2), 10 + (size / 2), 640 - (this.size / 2), 360 - (size / 2)};
    private int speed = 2;
    public int type = 3;

    public Bullet(float x, float y, float dir) {
        this.positionX = x;
        this.positionY = y;
        this.direction = dir;
        cos = Math.cos(Math.toRadians(direction));
        sin = Math.sin(Math.toRadians(direction));
    }

    public boolean update(ArrayList<LineSegment> segments) {//1 is add 0 is take
        return collisionCheck(segments, positionX, positionY);
    }

    private boolean collisionCheck(ArrayList<LineSegment> segments, double changeX, double changeY){
        float startX = positionX;
        float startY = positionY;
        this.positionX += (float) (speed * (cos));
        this.positionY += (float) (speed * (sin));

        if (positionX > DIMENSIONS[2] || positionY > DIMENSIONS[3] || positionX < DIMENSIONS[0] || positionY < DIMENSIONS[1]){
            return true;//true means collision
        }

        if (startX != positionX || startY != positionY) {
            double perpendicular;
            for (int i = 0; i < segments.size(); i++) {//goes through all segments
                LineSegment segment = segments.get(i);//gets a segment
                if (segment.angleDeg != 0) {//for all lines that arnt vertical
                    perpendicular = segment.angleRad - (Math.PI / 2);
                    double perpX = size / 2 * Math.cos(perpendicular);
                    double perpY = size / 2 * Math.sin(perpendicular);//-90 degrees basically to get the perpendicular
                    Point a = new Point((positionX + perpX), (positionY + perpY));//a point out in front
                    Point b = new Point((positionX - perpX), (positionY - perpY));//a point beind
                    //these 4 lines ^ create a line that is perpendicular to the line it is near
                    Point intersect = RayCast.intersectLines(new LineSegment(a, b, 1), segment);
                    if (BetweenX(intersect, segment) && BetweenY(intersect, segment)) {
                        if (RayCast.distance(intersect, positionX, positionY) <= size/2) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}