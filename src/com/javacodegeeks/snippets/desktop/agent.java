package com.javacodegeeks.snippets.desktop;

//tODO someone who gets java fix this cus i did the math but dunno how to convert to java best of luck xd
public class agent{
    public float positionX = 100;
    public float positionY = 100;
    public float direction = 0;//degrees
    public float speedX = 0;
    public float speedY = 0;
    public float rays = 100;
    public float fov = 90;
    public float anglePerRay = (float) (((fov * Math.PI)/180)/rays);

    public void agentMov (char keyPressed) {
        anglePerRay = (float) (((fov * Math.PI)/180)/rays);
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