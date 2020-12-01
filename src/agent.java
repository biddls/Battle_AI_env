//tODO someone who gets java fix this cus i did the math but dunno how to convert to java best of luck xd

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;



public class agent  {


    public float positionX = 280;
    public float positionY = 100;
    public float direction = 45;//degrees
    public int size = 10;
    public float rays = 50;
    public float fov = 45;
    public float anglePerRay = (float) 0;

    public void agentMov(char keyPressed, ArrayList<LineSegment> segments) {

        anglePerRay = (float) (((fov * Math.PI)/180)/rays);
        this.direction = this.direction % 360;
        if (this.direction < 0) this.direction += 360;

        if (keyPressed == 'q'){
            this.direction -= 1;
        }
        if (keyPressed == 'e'){
            this.direction += 1;
        }
        if (keyPressed == 'w'){
            collionCheck(segments, Math.cos(Math.toRadians(direction)),
                    Math.sin(Math.toRadians(direction)));
        }
        if (keyPressed == 's'){
            collionCheck(segments, -Math.cos(Math.toRadians(direction)),
                    -Math.sin(Math.toRadians(direction)));
        }
        if (keyPressed == 'd'){
            collionCheck(segments, -Math.cos(Math.toRadians(90-direction)),
                    Math.sin(Math.toRadians(90-direction)));
        }
        if (keyPressed == 'a'){
            collionCheck(segments, Math.cos(Math.toRadians(direction)),
                    -Math.sin(Math.toRadians(direction)));
        }

    }

    private void collionCheck(ArrayList<LineSegment> segments, double changex, double changey){
        float startX = positionX;
        float startY = positionY;
        double perpendicular = 0;
        for(int i = 0; i < segments.size(); i++)
        {
            LineSegment segment = segments.get(i);
            perpendicular = segment.angle - (Math.PI/2);
            Point a = new Point(positionX + size * Math.cos(perpendicular), positionY + size * Math.sin(perpendicular));
            Point b = new Point(positionX - size * Math.cos(perpendicular), positionY - size * Math.sin(perpendicular));
            LineSegment character = new LineSegment(a, b, 1);
            Point intersect = RayCast.getIntersection(character, segment, 0);
            //System.out.println(intersect);
        }
        this.positionX += (float) changex;
        this.positionY += (float) changey;
    }
}