import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Zombie extends Humanoid {
    public Zombie(Point p, int health, int size){
        this.positionX = p.x;
        this.positionY = p.y;
        this.health = health;
        this.size = size;
        this.keys = new char[] {'i', 'j', 'k', 'l', 'u', 'o'};
        this.DIMENSIONS = new int[] {DIMENSIONS[0] + size/2, DIMENSIONS[1] + size/2, DIMENSIONS[2] - size/2, DIMENSIONS[3] - size/2};
        this.type = 4;
    }
}
