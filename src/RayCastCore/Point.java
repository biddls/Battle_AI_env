package RayCastCore;

import java.awt.Color;

/**
 * Created by Armin on 10/19/2017.
 */

public class Point {

    public double x;
    public double y;
    public double distance;

    public int type;//0 is nothing, 1 is wall, 2 is agent, 3 is for bullet, 4 is zombie
    public double direction;
    public Color colour = new Color(255,255,255);


    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, int type) {
        this.x = x;
        this.y = y;
        this.type = Math.max(type, 0);
    }

    public Point(double x, double y, int type, double distance ) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")" + "||" + type + "|" + distance + ")";
    }

    public static Point subtract(Point a, Point b){
        return new Point(a.x - b.x, - a.y - - b.y);
    }
}


