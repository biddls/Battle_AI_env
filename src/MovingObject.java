import java.util.ArrayList;

public class MovingObject {
    public float positionX;
    public float positionY;
    public float direction = 0;//degrees
    public int size;
    public int health = 1;
    public int type = 0;//0 is nothing, 1 is wall, 2 is human, 3 is bullet, 4 is zombie
    public int[] DIMENSIONS = {10, 10, 640, 360};
    public int speed;

    public void collisionCheck(ArrayList<LineSegment> segments, double changeX, double changeY){
        float startX = positionX;
        float startY = positionY;
        positionX += (float) changeX;
        positionY += (float) changeY;

        if (type == 3) {
            if (positionX > DIMENSIONS[2] || positionY > DIMENSIONS[3] || positionX < DIMENSIONS[0] || positionY < DIMENSIONS[1]){
                health -= 1;
                return;
            }
        }else if (type == 2 || type == 4){
            positionX = (positionX > DIMENSIONS[2]) ? DIMENSIONS[2] : positionX;
            positionY = (positionY > DIMENSIONS[3]) ? DIMENSIONS[3] : positionY;
            positionX = (positionX < DIMENSIONS[0]) ? DIMENSIONS[0] : positionX;
            positionY = (positionY < DIMENSIONS[1]) ? DIMENSIONS[1] : positionY;
        }

        if (startX != positionX || startY != positionY) {
            double perpendicular;
            //gets a segment
            for (LineSegment segment : segments) {//goes through all segments
                if (segment.angleDeg != 0) {//for all lines that arnt vertical

                    perpendicular = segment.angleRad - (Math.PI / 2);
                    double perpX = size * 0.5 * Math.cos(perpendicular);
                    double perpY = size * 0.5 * Math.sin(perpendicular);//-90 degrees basically to get the perpendicular
                    Point a = new Point((positionX + perpX), (positionY + perpY));//a point out in front
                    Point b = new Point((positionX - perpX), (positionY - perpY));//a point beind
                    //these 4 lines ^ create a line that is perpendicular to the line it is near

                    Point intersect = RayCast.intersectLines(new LineSegment(a, b, 1), segment);
                    if (BetweenX(intersect, segment) && BetweenY(intersect, segment)) {
                        if (RayCast.distance(intersect, positionX, positionY) <= size * 0.5) {
                            if (type == 3) {
                                health -= 1;
                                return;
                            } else if (type == 2 || type == 4) {
                                this.positionX = (float) (startX - (2 * changeX));
                                this.positionY = (float) (startY - (2 * changeY));
                            }
                        }
                    }
                }
            }
        }
    }

    //i know these 2 could be more efficient, its just nicer this way
    public static boolean BetweenX(Point intersection, LineSegment segment){
        double min = Math.min(segment.A.x, segment.B.x);
        double max = Math.max(segment.A.x, segment.B.x);
        return min <= intersection.x && intersection.x <= max;
    }

    public static boolean BetweenY(Point intersection, LineSegment segment){
        double min = Math.min(segment.A.y, segment.B.y);
        double max = Math.max(segment.A.y, segment.B.y);
        return min <= intersection.y && intersection.y <= max;
    }
}
