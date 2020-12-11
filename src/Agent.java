//tODO someone who gets java fix this cus i did the math but dunno how to convert to java best of luck xd

import java.util.ArrayList;
import java.util.List;

public class Agent {

    public float positionX = 300;
    public float positionY = 150;
    public float direction = 0;//degrees
    public int size = 10;
    public float rays = 100;
    public float fov = 90;
    public float anglePerRay = (float) 0;


    public void agentMov(char keyPressed, ArrayList<LineSegment> segments) {
        //System.out.println(positionX + "|" + positionY);
        anglePerRay = (float) (((fov * Math.PI)/180)/rays);
        //this.direction = this.direction % 360;
        //if (this.direction < 0) this.direction += 360;

        switch (keyPressed){
            case 'q':
                this.direction -= 1;
                break;
            case 'e':
                this.direction += 1;
                break;
            case 'w':
                collisionCheck(segments, Math.cos(Math.toRadians(direction)),
                        Math.sin(Math.toRadians(direction)));
                break;
            case 's':
                collisionCheck(segments, -Math.cos(Math.toRadians(direction)),
                        -Math.sin(Math.toRadians(direction)));
                break;
            case 'd':
                collisionCheck(segments, -Math.cos(Math.toRadians(90-direction)),
                        Math.sin(Math.toRadians(90-direction)));
                break;
            case 'a':
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

        double perpendicular;
        for(int i = 0; i < segments.size(); i++){//goes through all segments
            LineSegment segment = segments.get(i);//gets a segment
            if (segment.angleDeg != 0) {//for all lines that arnt vertical
                perpendicular = segment.angleRad - (Math.PI / 2); //-90 degrees basically to get the perpendicular
                Point a = new Point((startX + size / 2 * Math.cos(perpendicular)), (startY + size / 2 * Math.sin(perpendicular)));//a point out in front
                Point b = new Point((startX - size / 2 * Math.cos(perpendicular)), (startY - size / 2 * Math.sin(perpendicular)));//a point beind
                //these 4 lines ^ create a line that is perpendicular to the line it is near
                Point intersect = RayCast.intersectLines(new LineSegment(a, b,1), segment);
                if (BetweenX(intersect, segment) && BetweenY(intersect, segment)){
                    if (RayCast.distance(intersect, positionX, positionY) <= 6) {
                        this.positionX = (float) (startX - (1 * changeX));
                        this.positionY = (float) (startY - (1 * changeY));
                    }
                }
//                if (intersect != null) {
////                    Point bouncePoint = RayCast.intersectLines(change, segment);
////                    this.bounce = bouncePoint.getPoint();
////                    //float c = (float) (bouncePoint.y - segment.angleGrad * bouncePoint.x);
////                    //float x = this.positionX;
////                    //float y = this.positionY;
////                    float m = (float) segment.angleGrad;
////                    //float m2 = (float) Math.pow(m, 2);
////                    this.positionX = (float) (startX - (5 * changeX));
////                    this.positionY = (float) (startY - (5 * changeY));
////                    /*
////                    this.positionX = (((1 - m2) * x) + (2 * m * y) - (2 * m * c)) / (m2 + 1);
////                    this.positionY = (((m2  - 1) * y) + (2 * m * x) + (2 * c)) / (m2 + 1);
////                    Point whereTo = new Point((((1 - m2) * x) + (2 * m * y) - (2 * m * c)) / (m2 + 1),
////                            (((m2  - 1) * y) + (2 * m * x) + (2 * c)) / (m2 + 1));
////                    System.out.println(positionX);
////                    System.out.println(positionY);*/
//                }
            }
        }
    }

    private boolean BetweenX(Point intersection, LineSegment segment){
        SimplePoint inter = intersection.getPoint();
        int min = Math.min(segment.A.x, segment.B.x);
        int max = Math.max(segment.A.x, segment.B.x);
        if(min <= inter.x && inter.x <= max){
            return true;
        }
        return false;
    }

    private boolean BetweenY(Point intersection, LineSegment segment){
        SimplePoint inter = intersection.getPoint();
        int min = Math.min(segment.A.y, segment.B.y);
        int max = Math.max(segment.A.y, segment.B.y);
        if(min <= inter.y && inter.y <= max){
            return true;
        }
        return false;
    }

}