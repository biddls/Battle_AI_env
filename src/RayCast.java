import java.util.ArrayList;

public class RayCast {

    public static final double EPSILON = 0.0001;
    private static final double RANGE = RayCastVisualizer.RANGE;

    public static double crossProduct(SimpleVector a, SimpleVector b) {
        return a.x * b.y - b.x * a.y;
    }

    public static double distance(SimplePoint a,SimplePoint b){return Math.sqrt(Math.pow(b.x-a.x,2)+Math.pow(b.y-a.y,2));}

    public static double distance(Point a,double x, double y){return Math.sqrt(Math.pow(x-a.x,2)+Math.pow(y-a.y,2));}

    public static double distance(Point a,Point b){return Math.sqrt(Math.pow(b.x-a.x,2)+Math.pow(b.y-a.y,2));}

    public static double distance(Point a,SimplePoint b){return Math.sqrt(Math.pow(b.x-a.x,2)+Math.pow(b.y-a.y,2));}

    public static double distance(SimplePoint a, SimplePoint b, int dw){return Math.pow(b.x-a.x,2)+Math.pow(b.y-a.y,2);}

    public static Point intersectLines(LineSegment change, LineSegment wall){
        float a = (float) change.angleGrad;
        float c = change.c;
        float b = (float) wall.angleGrad;
        float d = wall.c;
        float A = (d-c)/(a-b);
        float B = a*((d-c)/(a-b)) + c;
        return new Point((int) A, (int) B);
    }

    public static Point intersectLines(LineSegment ray, LineSegment wall, Soldier soldier){
        Line L1 = new Line(ray.A, ray.B);//A is Agent, B is end point
        Line L2 = new Line(wall.A, wall.B);//defines the wall

        if (L1.m != L2.m){//makes sure they arnt parallel
            double x;
            double y;
            if((Double.POSITIVE_INFINITY == L2.m) ^ (Double.POSITIVE_INFINITY * -1 == L2.m)){
                x = L2.A.x;//finds intersection point in the X axis
                y = L1.m * x + L1.c;//finds y intercept
            }else{
                x = (L1.c - L2.c)/(L2.m - L1.m);//finds intersection point in the X axis
                y = L1.m * x + L1.c;//finds y intercept
            }
            double dist = distance(new SimplePoint(x, y), ray.A.getPoint(), 0);//does not SQRT the answer to save computationaly
            if(dist <= Math.pow(RANGE, 2)) {//makes sure its within 800 units
                if (x <= Math.max(wall.b.x, wall.a.x) && x >= Math.min(wall.b.x, wall.a.x)){
                    Polar point = new Polar(ray.a, new SimplePoint(x, y));
                    double dir = 360 + (-soldier.direction % 360);
                    double limit1 = (90 + (soldier.direction - soldier.fov / 2)) % 360;
                    limit1 = Math.abs((720 - limit1) % 360);
                    double limit2 = (dir + soldier.fov / 2) % 360;
                    if(point.angle <= limit2 && point.angle >= limit1){
                        return new Point(x,y);
                    }
                    if(limit2 < 90) {
                        if (point.angle < 90) {
                            limit1 = 0;
                            //                            System.out.println(limit2 + "\t||\t" + dir + "\t|\t" + point.angle + "\t||\t" + limit1);
                        }
                        if (point.angle > 270) {
                            limit2 = 360;
                            //                            System.out.println(limit2 + "\t||\t" + dir + "\t|\t" + point.angle + "\t||\t" + limit1);
                        }
                    }
                    if(point.angle <= limit2 && point.angle >= limit1){
                        return new Point(x,y);
                    }
                }
            }
        }
        return null;
    }

    private static Point intersectCircleRay(LineSegment ray, float positionX, float positionY, int size) {
        double perpendicular = ray.angleRad - (Math.PI / 2); //-90 degrees basically to get the perpendicular
        double perpX = size / 2 * Math.cos(perpendicular);
        double perpY = size / 2 * Math.sin(perpendicular);
        if (ray.angleDeg != 0) {//for all lines that arnt vertical
            Point a = new Point((positionX + perpX), (positionY + perpY));//a point out in front
            Point b = new Point((positionX - perpX), (positionY - perpY));//a point beind
            //these 2 lines ^ create a line that is perpendicular to the line it is near
            Point intersect = RayCast.intersectLines(new LineSegment(a, b, 1), ray);
            if (Human.BetweenX(intersect, ray) && Human.BetweenY(intersect, ray)) {
                if (RayCast.distance(intersect, positionX, positionY) <= size/2) {
                    return new Point(positionX, positionY);
                }
            }

        }
        return null;
    }

    private static double PolarMath(double v) {
        v = (360 + v) % 360;
        return v;
    }

    private static int div(double a, double b) {
        return (int) (a/b);
    }

    public static Point getClosestIntersection(LineSegment ray,ArrayList<LineSegment> segments, Soldier angle){
        Point closestIntersect = null;
        double closestDistance = Double.MAX_VALUE;
        double closestDistanceTemp = Double.MAX_VALUE;

        for (LineSegment l : segments){
            Point intersect = intersectLines(ray, l, direction, fov);
            if (intersect != null){
                closestDistanceTemp = closestDistance;
                closestDistance = Math.min(closestDistance, distance(new SimplePoint(ray.A), new SimplePoint(intersect)));
                if (closestDistance != closestDistanceTemp){
                    closestIntersect = intersect;
                }
            }
        }
        for (Bullet b : env.bullets){
            Point intersect = intersectCircleRay(ray, b.positionX, b.positionY, b.size);
//            System.out.println(intersect);
            if (intersect != null){
                closestDistanceTemp = closestDistance;
                closestDistance = Math.min(closestDistance, distance(new SimplePoint(ray.A), new SimplePoint(intersect)));
                if (closestDistance != closestDistanceTemp){
                    closestIntersect = intersect;
                }
            }
        }

        return closestIntersect;
    }
}