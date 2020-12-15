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
    public int firing;


    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list){
        Set<T> set = new LinkedHashSet<>(list);
        return new ArrayList<T>(set);
    }

    public void Mov(char keyPressed, ArrayList<LineSegment> segments, int addOrTake) {//1 is add 0 is take
        if (addOrTake > -1) {
            if (type == 2) {
                if (keyPressed == ' ' && firing != -1) {
                    if (addOrTake == 1) {
                        this.firing = 1;
                    } else if (addOrTake == 0) {
                        this.firing = 0;
                    }
                    keyPressed = '1';
                }
            }

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
}
