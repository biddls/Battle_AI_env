//tODO someone who gets java fix this cus i did the math but dunno how to convert to java best of luck xd

public class agent{
    public float positionX = 280;
    public float positionY = 100;
    public float direction = 90;//degrees
    public float speedX = 1;
    public float speedY = 1;
    public float rays = 20;
    public float fov = 90;
    public float anglePerRay = (float) 0;

    public void agentMov(char keyPressed) {

        anglePerRay = (float) (((fov * Math.PI)/180)/rays);
        this.direction = this.direction % 360;

        if (this.direction < 0) this.direction += 360;
        if (keyPressed == 'q'){
            this.direction -= anglePerRay;
        }
        if (keyPressed == 'e'){
            this.direction += anglePerRay;
        }
        if (keyPressed == 'w'){
            this.positionX += Math.cos(Math.toRadians(direction));
            this.positionY += Math.sin(Math.toRadians(direction));
        }
        if (keyPressed == 's'){
            this.positionX -= Math.cos(Math.toRadians(direction));
            this.positionY -= Math.sin(Math.toRadians(direction));
        }
        if (keyPressed == 'a'){
            this.positionX -= Math.cos(Math.toRadians(90-direction));
            this.positionY += Math.sin(Math.toRadians(90-direction));
        }
        if (keyPressed == 'e'){
            this.positionX += Math.cos(Math.toRadians(direction));
            this.positionY -= Math.sin(Math.toRadians(direction));
        }

    }
}