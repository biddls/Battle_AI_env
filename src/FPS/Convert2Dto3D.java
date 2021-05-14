package FPS;

import RayCastCore.LineSegment;
import RayCastCore.Point;

import java.awt.*;

public class Convert2Dto3D {
    public static final double RANGE = 800;
    public static LineSegment[] convert(Point[] collisions, int rayNumber, int height){
        LineSegment[] segments = new LineSegment[collisions.length];
        int index = 0;
        for (Point p: collisions){
            //0 is nothing
            // 1 is wall
            // 2 is agent
            // 3 is for bullet
            // 4 is zombie
            switch (p.type){
                case 1:
                    segments[index] = wall(p, rayNumber, height, 1,1, p.colour);
                    break;
                case 3:
                    segments[index] = wall(p, rayNumber, height, 0.4,-0.2, p.colour);
                    break;
                case 4:
                    segments[index] = wall(p, rayNumber, height, 1,0.2, p.colour);
                    break;
            }
            index++;
        }
        return segments;
    }
    public static LineSegment wall(Point p, int rayNumber, int height, double scaleDown, double scaleUp, Color colour){
        double scalar = (15.0) / p.distance;
        double midY = ((double) height/2);
        Point top = new Point(rayNumber, midY - scalar*midY*scaleUp);
        Point bottom = new Point(rayNumber, midY + scalar*midY*scaleDown);
        LineSegment temp = new LineSegment(top, bottom, p.type, p.distance);
        temp.colour = colour;
        return temp;
    }
}
