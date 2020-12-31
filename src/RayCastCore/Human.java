package RayCastCore;

public class Human extends Humanoid{
    public Human(float x, float y, int health, int size){
        this.positionX = x;
        this.positionY = y;
        this.health = health;
        this.size = size;
        this.keys = new char[] {'w', 'a', 's', 'd', 'q', 'e'};
        this.DIMENSIONS = new int[] {DIMENSIONS[0] + size/2, DIMENSIONS[1] + size/2, DIMENSIONS[2] - size/2, DIMENSIONS[3] - size/2};
        this.type = 2;
    }
}