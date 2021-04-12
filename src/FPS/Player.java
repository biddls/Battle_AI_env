package FPS;

import RayCastCore.*;

import java.util.ArrayList;

public class Player extends Human{

    public ArrayList<Point[]> currentRays3D;
    public int rays = 1920;
    public float anglePerRay = (float) (((fov * Math.PI) / 180) / rays);
    public char[] keys = {'w', 'a', 's', 'd'};


    public Player(float x, float y, int health, int size) {
        super(x, y, health, size);
    }

    public void Fire(boolean firing){
        if (firing) {
            this.firing = 1;
        }else{
            this.firing = 0;
        }
    }

    public void Turn(int x){
        this.direction -= x/4;
    }

    @Override
    public void Mov(char keyPressed, ArrayList<LineSegment> segments, int addOrTake) {//1 is add 0 is take
        if (addOrTake > -1 && health > 0) {

            pressing = removeDuplicates(pressing);
            if (addOrTake == 1 && !pressing.contains(keyPressed)) {//add
                pressing.add(keyPressed);
            } else if (addOrTake == 0) {//take
                if (pressing.contains(keyPressed)) {
                    pressing.remove((Object) keyPressed);
                }
            }

            int forward = pressing.contains(keys[0]) ? 1 : 0;
            int strafeLeft = pressing.contains(keys[1]) ? 1 : 0;
            int backwards = pressing.contains(keys[2]) ? 1 : 0;
            int strafeRight = pressing.contains(keys[3]) ? 1 : 0;


            double dir = Math.toRadians(direction);
            double dir90 = Math.toRadians(90 - direction);
            double cos = Math.cos(dir);
            double cos90 = Math.cos(dir90);
            double sin = Math.sin(dir);
            double sin90 = Math.sin(dir90);

            double x = (forward * (cos)) + (strafeLeft * (cos90)) + (backwards * -(cos)) + (strafeRight * -(cos90));
            double y = (forward * (sin)) + (strafeLeft * -(sin90)) + (backwards * -(sin)) + (strafeRight * (sin90));

            collisionCheck(segments, x, y);
        }
    }

    public ArrayList<Point[]> castRays3D(ArrayList<LineSegment> LineSegments, Game self){

        ArrayList<Point[]> result = new ArrayList<>();
        float angleStart = (float) (((direction - (fov/2)) * Math.PI)/180);
        for (int i = 0; i < rays; i++) {
            Point target = new Point((int)(positionX+(Math.cos(anglePerRay*i + angleStart) * distance)),
                    (int)(positionY+Math.sin(anglePerRay*i + angleStart) * distance), (int) direction);
            target.distance = distance;
            //above returns a list of all the points around the mouse 800 units away will need to
            Point position = new Point((int) positionX,(int) positionY);
            LineSegment ray = new LineSegment(position,target,0);
            Point[] ci = RayCastFPS.getClosestIntersection3D(ray, LineSegments, self, direction, fov, type);
            if(ci[0] == null){
                System.out.println();
            }
            if (ci[0] == null) {result.add(new Point[] {target});} else {result.add(ci);}
        }
        return result;//B list of all points that the rays intersect with
    }
}
