import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Zombie extends Humanoid {
    this.keys = {'i', 'j', 'k', 'l', 'u', 'o'};

    public Zombie(Point p, int health, int size){
        this.positionX = p.x;
        this.positionY = p.y;
        this.health = health;
        this.size = size;
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list){
        Set<T> set = new LinkedHashSet<>(list);
        return new ArrayList<T>(set);
    }

    public void ZombieMov(char keyPressed, ArrayList<LineSegment> segments, int addOrTake) {//1 is add 0 is take
        if (addOrTake > -1) {
            pressing = removeDuplicates(pressing);
            if (addOrTake == 1 && !pressing.contains(keyPressed)) {//add
                pressing.add(keyPressed);
            } else if (addOrTake == 0) {//take
                if (pressing.contains(keyPressed)) {
                    pressing.remove((Object) keyPressed);
                }
            }

            int i = pressing.contains('i') ? 1 : 0;
            int j = pressing.contains('j') ? 1 : 0;
            int k = pressing.contains('k') ? 1 : 0;
            int l = pressing.contains('l') ? 1 : 0;
            int u = pressing.contains('u') ? 1 : 0;
            int o = pressing.contains('o') ? 1 : 0;

//            ArrayList<Integer> onOrOff = new ArrayList<>(Arrays.asList(w, a, s, d, q, e));

            this.direction = (u == 1 ^ o == 1) ? (u * (this.direction - 1)) + (o * (this.direction + 1)) : this.direction;

            double dir = Math.toRadians(direction);
            double dir90 = Math.toRadians(90-direction);
            double cos = Math.cos(dir);
            double cos90 = Math.cos(dir90);
            double sin = Math.sin(dir);
            double sin90 = Math.sin(dir90);

            double x = (i * (cos)) + (j * (cos90)) + (k * -(cos)) + (l * -(cos90));
            double y = (i * (sin)) + (j * -(sin90)) + (k * -(sin)) + (l * (sin90));

            collisionCheck(segments, x, y);
        }
    }
}
