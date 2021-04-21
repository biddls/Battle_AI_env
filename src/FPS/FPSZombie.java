package FPS;
import RayCastCore.*;
import RayCastCore.Zombie;

import java.util.ArrayList;

public class FPSZombie extends Zombie{

    public ArrayList<Point[]> currentRays3D;
    public int rays;
    public float anglePerRay;

    public FPSZombie(double x, double y, int health, int size, int rays) {
        super(new Point(x, y), health, size);
        this.rays = rays;
        this.anglePerRay = (float) (((fov * Math.PI) / 180) / rays);
    }

    public ArrayList<Point[]> castRays3D(ArrayList<LineSegment> LineSegments, Game self){

        ArrayList<Point[]> result = new ArrayList<>();
        float angleStart = (float) (((direction - (fov/2)) * Math.PI)/180);
        for (int i = 0; i < rays; i++) {
            Point target = new Point((int)(positionX+(Math.cos(anglePerRay*i + angleStart) * distance)),
                    (int)(positionY+Math.sin(anglePerRay*i + angleStart) * distance), (int) direction);
            target.distance = distance;
            //above returns a list of all the points around the mouse 800 units away will need to
            Point position = new Point((int) positionX,(int) positionY);
            LineSegment ray = new LineSegment(position,target,0);
            Point[] ci = RayCastFPS.getClosestIntersection3DPlayer(ray, LineSegments, (GameFPS) self, direction, fov, type);
            if(ci[0] == null){
                System.out.println();
            }
            if (ci[0] == null) {result.add(new Point[] {target});} else {result.add(ci);}
        }
        return result;//B list of all points that the rays intersect with
    }
}
