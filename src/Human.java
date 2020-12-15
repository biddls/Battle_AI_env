import java.util.*;
import java.util.LinkedHashSet;

public class Human extends Humanoid{
    public int firing;

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list){
        Set<T> set = new LinkedHashSet<>(list);
        return new ArrayList<T>(set);
    }

    public Human(float x, float y, int health, int size){
        this.positionX = x;
        this.positionY = y;
        this.health = health;
        this.size = size;
    }

    public void agentMov(char keyPressed, ArrayList<LineSegment> segments, int addOrTake) {//1 is add 0 is take
        if (addOrTake > -1) {
            if (keyPressed == ' ' && firing != -1){
                if (addOrTake == 1){
                    this.firing = 1;
                }else if (addOrTake == 0){
                    this.firing = 0;
                }
                keyPressed = '1';
            }

            pressing = removeDuplicates(pressing);
            if (addOrTake == 1 && !pressing.contains(keyPressed)) {//add
                pressing.add(keyPressed);
            } else if (addOrTake == 0) {//take
                if (pressing.contains(keyPressed)) {
                    pressing.remove((Object) keyPressed);
                }
            }

            int w = pressing.contains('w') ? 1 : 0;
            int a = pressing.contains('a') ? 1 : 0;
            int s = pressing.contains('s') ? 1 : 0;
            int d = pressing.contains('d') ? 1 : 0;
            int q = pressing.contains('q') ? 1 : 0;
            int e = pressing.contains('e') ? 1 : 0;

//            ArrayList<Integer> onOrOff = new ArrayList<>(Arrays.asList(w, a, s, d, q, e));

            this.direction = (q == 1 ^ e == 1) ? (q * (this.direction - 1)) + (e * (this.direction + 1)) : this.direction;

            double dir = Math.toRadians(direction);
            double dir90 = Math.toRadians(90-direction);
            double cos = Math.cos(dir);
            double cos90 = Math.cos(dir90);
            double sin = Math.sin(dir);
            double sin90 = Math.sin(dir90);

            double x = (w * (cos)) + (a * (cos90)) + (s * -(cos)) + (d * -(cos90));
            double y = (w * (sin)) + (a * -(sin90)) + (s * -(sin)) + (d * (sin90));

            collisionCheck(segments, x, y);
        }
    }

}