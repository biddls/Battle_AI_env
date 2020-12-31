package FPS;
import RayCastCore.*;

import java.util.ArrayList;

public class RayCastFPS extends RayCast {

    static Point3D[] getClosestIntersection3D(LineSegment ray, ArrayList<LineSegment> segments, Game env, float direction, float fov, int HZ) {
        double closestDistanceWall = Double.MAX_VALUE;
        double closestDistanceBullet = Double.MAX_VALUE;
        double closestDistanceZombie = Double.MAX_VALUE;
        Point3D closestIntersectWall = null;
        Point3D closestIntersectBullet = null;
        Point3D closestIntersectZombie = null;
        double closestDistanceTemp;

        for (LineSegment l : segments) {
            Point intersect = intersectLines(ray, l, direction, fov);
            if (intersect != null) {
                closestDistanceTemp = closestDistanceWall;
                closestDistanceWall = Math.min(closestDistanceWall, distance(ray.A, intersect));
                if (closestDistanceWall != closestDistanceTemp) {
                    closestIntersectWall = new Point3D(intersect);
                    closestIntersectWall.distance = (int) closestDistanceWall;
                    closestIntersectWall.type = 1;
                }
            }
        }

        for (Bullet b : env.bullets) {
            Point intersect = intersectCircleRay(ray, b.positionX, b.positionY, b.size, b.type);
            if (intersect != null) {
                closestDistanceTemp = closestDistanceBullet;
                closestDistanceBullet = Math.min(closestDistanceBullet, distance(ray.A, intersect));
                if (closestDistanceBullet != closestDistanceTemp) {
                    closestIntersectBullet = new Point3D(intersect);
                    closestIntersectBullet.distance = (int) closestDistanceBullet;
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
                        closestIntersectZombie = new Point3D(intersect);
                        closestIntersectZombie.distance = (int) closestDistanceZombie;
                        closestIntersectZombie.type = 4;
                    }
                }
            }
        }
        if (closestDistanceWall < closestDistanceBullet && closestDistanceWall < closestDistanceZombie) {
            return new Point3D[]{closestIntersectWall};
        } else if (closestDistanceWall > closestDistanceBullet && closestDistanceWall > closestDistanceZombie) {
            if (closestDistanceBullet < closestDistanceZombie) {
                return new Point3D[]{closestIntersectBullet, closestIntersectZombie, closestIntersectWall};
            } else if (closestDistanceZombie < closestDistanceBullet) {
                return new Point3D[]{closestIntersectZombie, closestIntersectWall};
            }
        } else if (closestDistanceWall > closestDistanceBullet && closestDistanceWall < closestDistanceZombie) {
            return new Point3D[]{closestIntersectBullet, closestIntersectWall};
        } else if (closestDistanceWall < closestDistanceBullet && closestDistanceWall > closestDistanceZombie) {
            return new Point3D[]{closestIntersectZombie, closestIntersectWall};
        }
        return new Point3D[]{closestIntersectWall};
    }
}