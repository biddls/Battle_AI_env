package RayCastCore;

public class Zombie extends Humanoid {
    public Zombie(Point p, int health, int size){
        this.positionX = (float) p.x;
        this.positionY = (float) p.y;
        this.health = health;
        this.size = size;
        this.keys = new char[] {'i', 'j', 'k', 'l', 'u', 'o'};
        this.DIMENSIONS = new int[] {DIMENSIONS[0] + size/2, DIMENSIONS[1] + size/2, DIMENSIONS[2] - size/2, DIMENSIONS[3] - size/2};
        this.type = 4;
    }
}
