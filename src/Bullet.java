import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Bullet {

    public float positionX;
    public float positionY;
    public float direction;//degrees
    public double cos;//cos
    public double sin;//sin
    public int size = 10;
    private final int[] DIMENSIONS = {10 + (size / 2), 10 + (size / 2), 640 - (this.size / 2), 360 - (size / 2)};
    private int speed = 1;

    public Bullet(float x, float y, float dir) {
        this.positionX = x;
        this.positionY = y;
        this.direction = dir;
        cos = Math.cos(Math.toRadians(direction));
        sin = Math.sin(Math.toRadians(direction));
    }

    public void update(ArrayList<LineSegment> segments) {//1 is add 0 is take

        double x = speed * (cos);
        double y = speed * (sin);

        collisionCheck(segments, x, y);
    }

    private void collisionCheck(ArrayList<LineSegment> segments, double changeX, double changeY){
        float startX = positionX;
        float startY = positionY;
        this.positionX += (float) changeX;
        this.positionY += (float) changeY;

        positionX = (positionX > DIMENSIONS[2]) ? DIMENSIONS[2] : positionX;
        positionY = (positionY > DIMENSIONS[3]) ? DIMENSIONS[3] : positionY;
        positionX = (positionX < DIMENSIONS[0]) ? DIMENSIONS[0] : positionX;
        positionY = (positionY < DIMENSIONS[1]) ? DIMENSIONS[1] : positionY;

        if (startX != positionX || startY != positionY) {
            double perpendicular;
            for (int i = 0; i < segments.size(); i++) {//goes through all segments
                LineSegment segment = segments.get(i);//gets a segment
                if (segment.angleDeg != 0) {//for all lines that arnt vertical
                    perpendicular = segment.angleRad - (Math.PI / 2); //-90 degrees basically to get the perpendicular
                    Point a = new Point((startX + size / 2 * Math.cos(perpendicular)), (startY + size / 2 * Math.sin(perpendicular)));//a point out in front
                    Point b = new Point((startX - size / 2 * Math.cos(perpendicular)), (startY - size / 2 * Math.sin(perpendicular)));//a point beind
                    //these 4 lines ^ create a line that is perpendicular to the line it is near
                    Point intersect = RayCast.intersectLines(new LineSegment(a, b, 1), segment);
                    if (BetweenX(intersect, segment) && BetweenY(intersect, segment)) {
                        if (RayCast.distance(intersect, positionX, positionY) <= 6) {
                            this.positionX = (float) (startX - (2 * changeX));
                            this.positionY = (float) (startY - (2 * changeY));
                        }
                    }
                }
            }
        }
    }

    private boolean BetweenX(Point intersection, LineSegment segment){
        SimplePoint inter = intersection.getPoint();
        int min = Math.min(segment.A.x, segment.B.x);
        int max = Math.max(segment.A.x, segment.B.x);
        if(min <= inter.x && inter.x <= max){
            return true;
        }
        return false;
    }

    private boolean BetweenY(Point intersection, LineSegment segment){
        SimplePoint inter = intersection.getPoint();
        int min = Math.min(segment.A.y, segment.B.y);
        int max = Math.max(segment.A.y, segment.B.y);
        if(min <= inter.y && inter.y <= max){
            return true;
        }
        return false;
    }

}