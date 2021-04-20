package FPS;
import RayCastCore.*;

import java.util.ArrayList;

public class RayCastFPS extends RayCast {

    static Point[] getClosestIntersection3D(LineSegment ray, ArrayList<LineSegment> segments, Game env, double direction, float fov, int HZ) {
        double closestDistanceWall = Double.MAX_VALUE;
        double closestDistanceBullet = Double.MAX_VALUE;
        double closestDistanceZombie = Double.MAX_VALUE;
        Point closestIntersectWall = null;
        Point closestIntersectBullet = null;
        Point closestIntersectZombie = null;
        double closestDistanceTemp;

        for (LineSegment l : segments) {
            Point intersect = intersectLines(ray, l, direction, fov, l.type);
            if (intersect != null) {
                closestDistanceTemp = closestDistanceWall;
                closestDistanceWall = Math.min(closestDistanceWall, distance(ray.A, intersect));
                if (closestDistanceWall != closestDistanceTemp) {
                    closestIntersectWall = intersect;
                    closestIntersectWall.distance = closestDistanceWall;
                    closestIntersectWall.type = 1;
                    closestIntersectWall.colour = l.colour;
                }
            }
        }

        for (Bullet b : env.bullets) {
            Point intersect = intersectCircleRay(ray, b.positionX, b.positionY, b.size, b.type);
            if (intersect != null) {
                closestDistanceTemp = closestDistanceBullet;
                closestDistanceBullet = Math.min(closestDistanceBullet, distance(ray.A, intersect));
                if (closestDistanceBullet != closestDistanceTemp) {
                    closestIntersectBullet = intersect;
                    closestIntersectBullet.distance = closestDistanceBullet;
                    closestIntersectBullet.type = 3;
                }
            }
        }

        if (HZ == 2 && env.zombies.size() > 0) {
            for (Zombie z : env.zombies) {
                Point intersect = intersectCircleRay(ray, z.positionX, z.positionY, z.size, z.type);
                if (intersect != null) {
                    closestDistanceTemp = closestDistanceZombie;
                    closestDistanceZombie = Math.min(closestDistanceZombie, distance(ray.A, intersect));
                    if (closestDistanceZombie != closestDistanceTemp) {
                        closestIntersectZombie = intersect;
                        closestIntersectZombie.distance = closestDistanceZombie;
                        closestIntersectZombie.type = 4;
                    }
                }
            }
        }
        if (closestDistanceWall < closestDistanceBullet && closestDistanceWall < closestDistanceZombie) {
            return new Point[]{closestIntersectWall};
        } else if (closestDistanceWall > closestDistanceBullet && closestDistanceWall > closestDistanceZombie) {
            if (closestDistanceBullet < closestDistanceZombie) {
                return new Point[]{closestIntersectBullet, closestIntersectZombie, closestIntersectWall};
            } else if (closestDistanceZombie < closestDistanceBullet) {
                return new Point[]{closestIntersectZombie, closestIntersectWall};
            }
        } else if (closestDistanceWall > closestDistanceBullet && closestDistanceWall < closestDistanceZombie) {
            return new Point[]{closestIntersectBullet, closestIntersectWall};
        } else if (closestDistanceWall < closestDistanceBullet && closestDistanceWall > closestDistanceZombie) {
            return new Point[]{closestIntersectZombie, closestIntersectWall};
        }else{
            return new Point[]{closestIntersectWall};
        }
        return new Point[]{closestIntersectWall};
    }
}