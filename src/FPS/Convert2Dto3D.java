package FPS;

import RayCastCore.Point;

public class Convert2Dto3D {
    public static final double RANGE = 800;
    public static LineSegment3D[] convert(Point3D[] collisions, int rayNumber, int height){
        LineSegment3D[] segments = new LineSegment3D[collisions.length];
        int index = 0;
        for (Point3D p: collisions){
            //0 is nothing
            // 1 is wall
            // 2 is agent
            // 3 is for bullet
            // 4 is zombie
            switch (p.type){
                case 1:
                    segments[index] = wall(p, rayNumber, height, 1,1);
                    break;
                case 3:
                    segments[index] = wall(p, rayNumber, height, 0.2,0.2);
                    break;
                case 4:
                    segments[index] = wall(p, rayNumber, height, 1,0.2);
                    break;
            }
            index++;
        }
        return segments;
    }
    public static LineSegment3D wall(Point3D p, int rayNumber, int height, double scaleDown, double scaleUp){
        double scalar = (15.0) / p.distance;
        double midY = ((double) height/2);
//        double sca = 15;
        Point3D top = new Point3D(rayNumber, midY - scalar*midY*scaleUp);
        Point3D bottom = new Point3D(rayNumber, midY + scalar*midY*scaleDown);
        return new LineSegment3D(top, bottom, p.type, p.distance);
    }
}
