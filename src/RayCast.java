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

    // Find intersection of RAY & SEGMENT
    // returns null if no intersection found
    public static Point getIntersection(LineSegment ray, LineSegment segment, int type){//TODO: allow it to return the type of intersections, enemy etc
        SimpleVector r = new SimpleVector(ray.b.x - ray.a.x, ray.b.y - ray.a.y);
        SimpleVector s = new SimpleVector(segment.b.x - segment.a.x, segment.b.y - segment.a.y);
        double rxs = crossProduct(r, s);
        SimpleVector qxp = new SimpleVector(segment.a.x - ray.a.x, segment.a.y - ray.a.y);
        double qpxr = crossProduct(qxp, r);
        // If r x s = 0 and (q - p) x r = 0, then the two lines are collinear.
        if (rxs < EPSILON && qpxr < EPSILON) {
            // 1. If either  0 <= (q - p) * r <= r * r or 0 <= (p - q) * s <= * s
            // then the two lines are overlapping,
            /*if (considerCollinearOverlapAsIntersect)
                if ((0 <= (q - p)*r && (q - p)*r <= r*r) || (0 <= (p - q)*s && (p - q)*s <= s*s))
                    return true;*/

            // 2. If neither 0 <= (q - p) * r = r * r nor 0 <= (p - q) * s <= s * s
            // then the two lines are collinear but disjoint.
            // No need to implement this expression, as it follows from the expression above.
            return null;
        }

        if (rxs < EPSILON && qpxr >= EPSILON) {
            //then the two lines are parallel and non-intersecting.
            return null;
        }

        // t = (q - p) x s / (r x s)
        //var t = (q - p).Cross(s)/rxs;
        double t = crossProduct(qxp, s) / rxs;
        // u = (q - p) x r / (r x s)
        //var u = (q - p).Cross(r)/rxs;
        double u = crossProduct(qxp, r) / rxs;
        // 4. If r x s != 0 and 0 <= t <= 1 and 0 <= u <= 1
        // the two line segments meet at the point p + t r = q + u s.
        if (rxs >= EPSILON && (0 <= t && t <= 1) && (0 <= u && u <= 1)) {
            // We can calculate the intersection point using either t or u.
            //intersection = p + t*r;

            return new Point((int) Math.floor(ray.a.x + t * r.x), (int) Math.floor(ray.a.y + t * r.y), type);

            // An intersection was found.
            //return true;
        }
        return null;
        // 5. Otherwise, the two line segments are not parallel but do not intersect.
    }

    public static Point intersectLines(LineSegment change, LineSegment wall){
        float a = (float) change.angleGrad;
        float c = change.c;
        float b = (float) wall.angleGrad;
        float d = wall.c;
        float A = (d-c)/(a-b);
        float B = a*((d-c)/(a-b)) + c;
        //System.out.println(A);
        //System.out.println(B);
        return new Point((int) A, (int) B);
    }

    public static Point intersectLines(LineSegment ray, LineSegment wall, Agent agent){
        Line L1 = new Line(ray.A, ray.B);//A is Agent, B is end point
        Line L2 = new Line(wall.A, wall.B);
        if (L1.m != L2.m){//makes sure they arnt parallel
            double x = (L1.c - L2.c)/(L2.m - L1.m);//finds intersection point in the X axis
            double y = L1.m * x + L1.c;//finds y intercept
            double dist = distance(new SimplePoint(x, y), ray.A.getPoint(), 0);//does not SQRT the answer to save computationaly
            if(dist <= Math.pow(RANGE, 2)) {//makes sure its within 800 units
                if (x <= Math.max(wall.b.x, wall.a.x) && x >= Math.min(wall.b.x, wall.a.x)){
                    Polar point = new Polar(ray.a, new SimplePoint(x, y));
                    double dir = Math.abs((720 - agent.direction) % 360);
                    double limit1 = (90 + (agent.direction - agent.fov / 2)) % 360;
                    limit1 = Math.abs((720 - limit1) % 360);
                    double limit2 = (dir + agent.fov / 2) % 360;
                    //System.out.println(limit2 + "|" + point.angle + "|" + limit1);
                    if(limit2 >= agent.fov){
                        limit2 -= limit1;
                        point.angle -= limit1;
                        limit1 = 0;
                    }else if(limit2 < agent.fov){
                        limit2 = (360- limit1) + limit2;
                        point.angle = (360-limit1) + limit2;
                        limit1 = 0;
                    }
                    if(point.angle <= limit2 && point.angle >= limit1){
                        return new Point(x,y);
                    }
                }
            }
        }
        return null;
    }

    public static Point getClosestIntersection(LineSegment ray,ArrayList<LineSegment> segments, Agent angle){
        Point closestIntersect = null;
        double closestDistance = Double.MAX_VALUE;
        double closestDistanceTemp = Double.MAX_VALUE;

        for(LineSegment l : segments){
            Point intersect = intersectLines(ray, l, angle);
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