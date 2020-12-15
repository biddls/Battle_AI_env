import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Humanoid extends MovingObject{
    public float rays = 50;
    public float fov = 90;
    public float anglePerRay = (float) (((fov * Math.PI) / 180) / rays);
    public ArrayList pressing = new ArrayList<>();
    public static int distance = 800;//degrees
    public ArrayList<Point> currentRays;
    public char[] keys = {' ', ' ', ' ', ' ', ' ', ' '};


    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list){
        Set<T> set = new LinkedHashSet<>(list);
        return new ArrayList<T>(set);
    }

    public void Mov(char keyPressed, ArrayList<LineSegment> segments, int addOrTake) {//1 is add 0 is take
        if (addOrTake > -1) {
            //add firing class for human
//            if (keyPressed == ' ' && firing != -1) {
//                if (addOrTake == 1) {
//                    this.firing = 1;
//                } else if (addOrTake == 0) {
//                    this.firing = 0;
//                }
//                keyPressed = '1';
//            }

            pressing = removeDuplicates(pressing);
            if (addOrTake == 1 && !pressing.contains(keyPressed)) {//add
                pressing.add(keyPressed);
            } else if (addOrTake == 0) {//take
                if (pressing.contains(keyPressed)) {
                    pressing.remove((Object) keyPressed);
                }
            }

            int forward = pressing.contains(keys[0]) ? 1 : 0;
            int strafeLeft = pressing.contains(keys[1]) ? 1 : 0;
            int backwards = pressing.contains(keys[2]) ? 1 : 0;
            int strafeRight = pressing.contains(keys[3]) ? 1 : 0;
            int turnLeft = pressing.contains(keys[4]) ? 1 : 0;
            int turnRight = pressing.contains(keys[5]) ? 1 : 0;

//            ArrayList<Integer> onOrOff = new ArrayList<>(Arrays.asList(forward, strafeLeft, backwards, strafeRight, turnLeft, turnRight));

            this.direction = (turnLeft == 1 ^ turnRight == 1) ? (turnLeft * (this.direction - 1)) + (turnRight * (this.direction + 1)) : this.direction;

            double dir = Math.toRadians(direction);
            double dir90 = Math.toRadians(90 - direction);
            double cos = Math.cos(dir);
            double cos90 = Math.cos(dir90);
            double sin = Math.sin(dir);
            double sin90 = Math.sin(dir90);

            double x = (forward * (cos)) + (strafeLeft * (cos90)) + (backwards * -(cos)) + (strafeRight * -(cos90));
            double y = (forward * (sin)) + (strafeLeft * -(sin90)) + (backwards * -(sin)) + (strafeRight * (sin90));

            collisionCheck(segments, x, y);
        }
    }


    public void collisionCheck(ArrayList<LineSegment> segments, double changeX, double changeY){
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
                    perpendicular = segment.angleRad - (Math.PI / 2);//-90 degrees basically to get the perpendicular
                    double perpX = size / 2 * Math.cos(perpendicular);
                    double perpY = size / 2 * Math.sin(perpendicular);
                    Point a = new Point((positionX + perpX), (positionY + perpY));//a point out in front
                    Point b = new Point((positionX - perpX), (positionY - perpY));//a point beind
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
}
