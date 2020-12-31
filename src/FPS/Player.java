package FPS;

import RayCastCore.*;

import java.util.ArrayList;

public class Player extends Human{

    public ArrayList<Point3D[]> currentRays3D;
    public int rays = 1080;

    public Player(float x, float y, int health, int size) {
        super(x, y, health, size);
    }

    public ArrayList<Point3D[]> castRays3D(ArrayList<LineSegment> LineSegments, Game self){

        ArrayList<Point3D[]> result = new ArrayList<>();
        float angleStart = (float) (((direction - (fov/2)) * Math.PI)/180);
        for (int i = 0; i < rays; i++) {
            Point3D target = new Point3D((int)(positionX+(Math.cos(anglePerRay*i + angleStart) * distance)),
                    (int)(positionY+Math.sin(anglePerRay*i + angleStart) * distance), direction);
            target.distance = distance;
            //above returns a list of all the points around the mouse 800 units away will need to
            Point position = new Point((int) positionX,(int) positionY);
            LineSegment ray = new LineSegment(position,target,0);
            Point3D[] ci = RayCastFPS.getClosestIntersection3D(ray, LineSegments, self, direction, fov, type);
            if (ci[0] == null) {result.add(new Point3D[] {target});} else {result.add(ci);}
        }
        return result;//B list of all points that the rays intersect with
    }
}
