package RayCastCore;
import java.awt.Color;

public class LineSegment {

    public Point A;
    public Point B;
    public int type;
    public Vector dir;
    public double angleDeg;
    public double angleRad;
    public double angleGrad;
    public float length;
    public float c;
    public double distance;
    public Color colour = new Color(255, 255, 255);

    public LineSegment(Point A, Point B, int type){
        this.A = A;
        this.B = B;
        this.type = type;
        dir = new Vector(B.x-A.x, B.y-A.y);
        if (type == 0) {//if ray it gets the length of it
            this.length = (float) Math.sqrt(Math.pow(dir.x, 2) +
                    Math.pow(dir.y, 2));
        }
        if (A.x - B.x != 0) {//if a wall it gets the angle of it
            this.angleRad = Math.atan((A.y - B.y) / (A.x - B.x));
            this.angleDeg = Math.toDegrees(angleRad);
            this.angleGrad = (A.y - B.y) / (A.x - B.x);
            this.c = (float) (A.y - angleGrad * A.x);
        }
    }

    public LineSegment(Point A, Point B, int type, double distance) {
        this.A = A;
        this.B = B;
        this.type = type;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "(" + A + " -> " + B + "||" + type + "|"+ distance + ")";
    }
}