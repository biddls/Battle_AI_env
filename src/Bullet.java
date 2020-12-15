public class Bullet extends MovingObject{
    public double cos;//cos
    public double sin;//sin

    public Bullet(float x, float y, float dir, int size, int speed) {
        this.positionX = x;
        this.positionY = y;
        this.direction = dir;
        this.speed = speed;
        this.cos = speed * Math.cos(Math.toRadians(direction));
        this.sin = speed * Math.sin(Math.toRadians(direction));
        this.DIMENSIONS = new int[] {DIMENSIONS[0] + size/2, DIMENSIONS[1] + size/2, DIMENSIONS[2] - size/2, DIMENSIONS[3] - size/2};
        this.type = 3;
        this.size = size;
    }
}