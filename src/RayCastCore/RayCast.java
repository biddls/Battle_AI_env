package RayCastCore;

import java.util.ArrayList;

public class RayCast {
    private static final double RANGE = 800;

    public static double distance(Point a,Point b){return Math.sqrt(Math.pow(b.x-a.x,2)+Math.pow(b.y-a.y,2));}

    public static double distance(Point a,double x, double y){return Math.sqrt(Math.pow(x-a.x,2)+Math.pow(y-a.y,2));}

    public static double distanceEff(Point a, Point b){return Math.pow(b.x-a.x,2)+Math.pow(b.y-a.y,2);}

    public static double distanceEff(double x1, double y1, double x2, double y2){return Math.pow(x1-x2,2)+Math.pow(y1-y2,2);}

    public static Point intersectLines(LineSegment change, LineSegment wall){
        float a = (float) change.angleGrad;
        float c = change.c;
        float b = (float) wall.angleGrad;
        float d = wall.c;
        float A = (d-c)/(a-b);
        float B = a*((d-c)/(a-b)) + c;
        return new Point((int) A, (int) B);
    }

    public static Point intersectLines(LineSegment ray, LineSegment wall, float direction, float fov){
        Line L1 = new Line(ray.A, ray.B);//A is RayCast.Human, B is end point
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
            double dist = distanceEff(new Point(x, y), ray.A);//does not SQRT the answer to save computationaly
            if(dist <= Math.pow(RANGE, 2)) {//makes sure its within 800 units
                if (x <= Math.max(wall.B.x, wall.A.x) && x >= Math.min(wall.B.x, wall.A.x)){
                    Polar point = new Polar(ray.A, new Point(x, y));
                    double dir = 360 + (-direction % 360);
                    double limit1 = (90 + (direction - fov / 2)) % 360;
                    limit1 = Math.abs((720 - limit1) % 360);
                    double limit2 = (dir + fov / 2) % 360;
                    if(point.angle <= limit2 && point.angle >= limit1){
                        return new Point(x,y, wall.type);
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
                        return new Point(x,y, wall.type);
                    }
                }
            }
        }
        return null;
    }

    protected static Point intersectCircleRay(LineSegment ray, double positionX, double positionY, int size, int type) {
        double perpendicular = ray.angleRad - (Math.PI / 2); //-90 degrees basically to get the perpendicular
        double perpX = size / 2 * Math.cos(perpendicular);
        double perpY = size / 2 * Math.sin(perpendicular);
        if (ray.angleDeg != 0) {//for all lines that arnt vertical
            Point a = new Point((positionX + perpX), (positionY + perpY));//a point out in front
            Point b = new Point((positionX - perpX), (positionY - perpY));//a point beind
            //these 2 lines ^ create a line that is perpendicular to the line it is near
            Point intersect = RayCast.intersectLines(new LineSegment(a, b, type), ray);
            if (Human.BetweenX(intersect, ray) && Human.BetweenY(intersect, ray)) {
                if (RayCast.distance(intersect, positionX, positionY) <= size/2) {
                    return intersect;
                }
            }
        }
        return null;
    }

    public static boolean CirclesCollision(double positionX1, double positionY1, int size1, double positionX2, double positionY2, int size2) {
        return distanceEff(positionX1, positionY1, positionX2, positionY2) <= (Math.pow(size1/2 + size2/2,2));
    }

    public static Point getClosestIntersection(LineSegment ray, ArrayList<LineSegment> segments, Game env, float direction, float fov, int HZ){
        Point closestIntersect = null;
        double closestDistance = Double.MAX_VALUE;
        double closestDistanceTemp;

        for (LineSegment l : segments){
            Point intersect = intersectLines(ray, l, direction, fov);
            if (intersect != null){
                closestDistanceTemp = closestDistance;
                closestDistance = Math.min(closestDistance, distance(ray.A, intersect));
                if (closestDistance != closestDistanceTemp){
                    closestIntersect = intersect;
                }
            }
        }

        for (Bullet b : env.bullets) {
            Point intersect = intersectCircleRay(ray, b.positionX, b.positionY, b.size, b.type);
            if (intersect != null) {
                closestDistanceTemp = closestDistance;
                closestDistance = Math.min(closestDistance, distance(ray.A, intersect));
                if (closestDistance != closestDistanceTemp) {
                    closestIntersect = intersect;
                }
            }
        }

        if (HZ == 2 && env.zombies.size() > 0){
            for (Zombie z : env.zombies) {
                Point intersect = intersectCircleRay(ray, z.positionX, z.positionY, z.size, z.type);
                if (intersect != null) {
                    closestDistanceTemp = closestDistance;
                    closestDistance = Math.min(closestDistance, distance(ray.A, intersect));
                    if (closestDistance != closestDistanceTemp) {
                        closestIntersect = intersect;
                    }
                }
            }
        }else if ((HZ == 4 && env.zombies.size() > 0) && env.human1.health > 0){
            Point intersect = intersectCircleRay(ray, env.human1.positionX, env.human1.positionY, env.human1.size, env.human1.type);
            if (intersect != null) {
                closestDistanceTemp = closestDistance;
                closestDistance = Math.min(closestDistance, distance(ray.A, intersect));
                if (closestDistance != closestDistanceTemp) {
                    closestIntersect = intersect;
                }
            }
        }
        return closestIntersect;
    }
}
