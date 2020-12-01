//tODO someone who gets java fix this cus i did the math but dunno how to convert to java best of luck xd

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;



public class agent extends KeyAdapter {

    public float positionX = 630;
    public float positionY = 350;
    public float direction = 220;//degrees
    public int size = 10;
    public float rays = 50;
    public float fov = 45;
    public float anglePerRay = (float) 0;

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        //System.out.println(e);
    }

    public void agentMov(char keyPressed, ArrayList<LineSegment> segments) {
        anglePerRay = (float) (((fov * Math.PI)/180)/rays);
        this.direction = this.direction % 360;

        if (this.direction < 0) this.direction += 360;
        switch (keyPressed){
            case 'q':
                this.direction -= anglePerRay;
                break;
            case 'e':
                this.direction += anglePerRay;
                break;
            case 'w':
                collisionCheck(segments, Math.cos(Math.toRadians(direction)),
                        Math.sin(Math.toRadians(direction)));
                break;
            case 's':
                collisionCheck(segments, -Math.cos(Math.toRadians(direction)),
                        -Math.sin(Math.toRadians(direction)));
                break;
            case 'a':
                collisionCheck(segments, -Math.cos(Math.toRadians(90-direction)),
                        Math.sin(Math.toRadians(90-direction)));
                break;
            case 'd':
                collisionCheck(segments, Math.cos(Math.toRadians(90-direction)),
                        -Math.sin(Math.toRadians(90-direction)));
                break;
        }
    }

    private void collisionCheck(ArrayList<LineSegment> segments, double changeX, double changeY){
        float startX = positionX;
        float startY = positionY;
        this.positionX += (float) changeX;
        this.positionY += (float) changeY;
        Point start = new Point(startX, startY);
        Point end = new Point(positionX, positionY);
        LineSegment change = new LineSegment(start, end, 1);

        double perpendicular;
        for(int i = 0; i < segments.size(); i++){
            LineSegment segment = segments.get(i);
            perpendicular = segment.angleRad - (Math.PI/2); //-90 degrees basically to get the perpendicular
            Point a = new Point((startX + size/2 * Math.cos(perpendicular)),(startY + size/2 * Math.sin(perpendicular)));
            Point b = new Point((startX - size/2 * Math.cos(perpendicular)),(startY - size/2 * Math.sin(perpendicular)));
            LineSegment character = new LineSegment(a, b, 1);
            Point intersect = RayCast.getIntersection(character, segment, 0);
            if (intersect != null){
                Point bouncePoint = RayCast.intersectLines(change, segment);
                //float c = (float) (bouncePoint.y - segment.angleGrad * bouncePoint.x);
                //float x = this.positionX;
                //float y = this.positionY;
                float m = (float) segment.angleGrad;
                //float m2 = (float) Math.pow(m, 2);
                this.positionX = (float) (startX - changeX);
                this.positionY = (float) (startY - changeY);
                /*
                this.positionX = (((1 - m2) * x) + (2 * m * y) - (2 * m * c)) / (m2 + 1);
                this.positionY = (((m2  - 1) * y) + (2 * m * x) + (2 * c)) / (m2 + 1);
                Point whereTo = new Point((((1 - m2) * x) + (2 * m * y) - (2 * m * c)) / (m2 + 1),
                        (((m2  - 1) * y) + (2 * m * x) + (2 * c)) / (m2 + 1));
                System.out.println(positionX);
                System.out.println(positionY);*/
            }
        }
    }
}