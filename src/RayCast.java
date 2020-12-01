import java.util.ArrayList;

public class RayCast {

    public static final double EPSILON = 0.0001;

    public static double crossProduct(SimpleVector a, SimpleVector b) {
        return a.x * b.y - b.x * a.y;

    }

    public static double distance(SimplePoint a,SimplePoint b){
        return Math.sqrt(Math.pow(b.x-a.x,2)+Math.pow(b.y-a.y,2));
    }

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
            System.out.println(segment);
            System.out.println(ray);
            System.out.println("next1");
            return null;
        }

        if (rxs < EPSILON && qpxr >= EPSILON) {
            //then the two lines are parallel and non-intersecting.
            System.out.println(segment);
            System.out.println(ray);
            System.out.println("next2");
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

    public static Point getClosestIntersection(LineSegment ray,ArrayList<LineSegment> segments){
        Point closestIntersect = null;
        double closestDistance = Double.MAX_VALUE;
        double closestDistanceTemp = Double.MAX_VALUE;

        for(LineSegment l : segments){
            Point intersect = getIntersection(ray, l, l.type);
            if (intersect != null){
                closestDistanceTemp = closestDistance;
                closestDistance = Math.min(closestDistance, distance(new SimplePoint(ray.A), new SimplePoint(intersect)));
                if (closestDistance != closestDistanceTemp){
                    closestIntersect = intersect;
                }
            }
            /*
            Point intersect = getIntersection(ray, l, l.type);
            if(intersect == null){
                System.out.println("segment");
                System.out.println(l);
                System.out.println("ray");
                System.out.println(ray);
                continue;
            }
            if(closestIntersect == null || distance(new SimplePoint(ray.A), new SimplePoint(intersect)) < closestDistance){
                closestIntersect = intersect ;
                closestDistance = distance(new SimplePoint(ray.A), new SimplePoint(intersect));
            }*/
        }
        return closestIntersect;
    }
}
