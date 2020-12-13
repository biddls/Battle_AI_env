//tODO someone who gets java fix this cus i did the math but dunno how to convert to java best of luck xd

import java.util.ArrayList;
import java.util.List;

public class Agent {

    public float positionX = 300;
    public float positionY = 150;
    public float direction = 0;//degrees
    public int size = 10;
    public float rays = 50;
    public float fov = 90;
    public float anglePerRay = (float) 0;
    private final int[] DIMENSIONS = {10 + (size / 2), 10 + (size / 2), 640 - (this.size / 2), 360 - (size / 2)};


    public void agentMov(char keyPressed, ArrayList<LineSegment> segments) {
        anglePerRay = (float) (((fov * Math.PI)/180)/rays);

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

        positionX = (positionX > DIMENSIONS[2]) ? DIMENSIONS[2] : positionX;
        positionY = (positionY > DIMENSIONS[3]) ? DIMENSIONS[3] : positionY;
        positionX = (positionX < DIMENSIONS[0]) ? DIMENSIONS[0] : positionX;
        positionY = (positionY < DIMENSIONS[1]) ? DIMENSIONS[1] : positionY;

        if (startX != positionX || startY != positionY) {
            double perpendicular;
            for (int i = 0; i < segments.size(); i++) {//goes through all segments
                LineSegment segment = segments.get(i);//gets a segment
                if (segment.angleDeg != 0) {//for all lines that arnt vertical
                    perpendicular = segment.angleRad - (Math.PI / 2); //-90 degrees basically to get the perpendicular
                    Point a = new Point((startX + size / 2 * Math.cos(perpendicular)), (startY + size / 2 * Math.sin(perpendicular)));//a point out in front
                    Point b = new Point((startX - size / 2 * Math.cos(perpendicular)), (startY - size / 2 * Math.sin(perpendicular)));//a point beind
                    //these 4 lines ^ create a line that is perpendicular to the line it is near
                    Point intersect = RayCast.intersectLines(new LineSegment(a, b, 1), segment);
                    if (BetweenX(intersect, segment) && BetweenY(intersect, segment)) {
                        if (RayCast.distance(intersect, positionX, positionY) <= 6) {
                            this.positionX = (float) (startX - (2 * changeX));
                            this.positionY = (float) (startY - (2 * changeY));
                        }
                    }
                }
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