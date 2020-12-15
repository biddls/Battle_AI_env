import java.util.ArrayList;

public class Bullet extends MovingObject{
    private final double cos;//cos
    private final double sin;//sin
    public int size = 6;//gotta be even

    public Bullet(float x, float y, float dir) {
        this.positionX = x;
        this.positionY = y;
        this.direction = dir;
        cos = Math.cos(Math.toRadians(direction));
        sin = Math.sin(Math.toRadians(direction));
        this.DIMENSIONS = new int[] {DIMENSIONS[0] + size/2, DIMENSIONS[1] + size/2, DIMENSIONS[2] - size/2, DIMENSIONS[3] - size/2};
        this.speed = 2;
        this.type = 3;
    }

    public void update(ArrayList<LineSegment> segments) {//1 is add 0 is take
        collisionCheck(segments, (speed * (cos)), (speed * (sin)));
    }
}