import java.util.ArrayList;

public class Game {
    public int zombieCount = 1;
    public int healthHum = 3;
    public int healthZom = 1;
    public int ZomReach = 5;
    public int scoreHum = 0;
    public int scoreZom = 0;
    public int damage = 1;
    public int magazine = 8;
    public Human human1 = new Human();
    public ArrayList<LineSegment> LineSegments;
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Zombie> zombies = new ArrayList<>();

    public Game(ArrayList<LineSegment> walls){
        this.LineSegments = walls;
        for(int i = 0; i < zombieCount; i++) {
            zombies.add(new Zombie(spawnPoint()));
        }
    }

    private Point spawnPoint(){
        return new Point(50,50);
    }

    public void fired(){
        bullets.add(new Bullet(human1.positionX, human1.positionY, human1.direction));
    }

    public void update(){
        if (bullets != null) {
            bullets.removeIf(b -> b.update(LineSegments));
            //do bullet zombie collision stuff
        }
        if (human1.health > 0) {
            human1.currentRays = castRaysH(human1);
        }
        if (zombies.size() > 0) {
            zombies.get(0).currentRays = castRaysZ(zombies.get(0));
        }
        collisions();
    }

    public ArrayList<Point> castRaysH(Human src){

        ArrayList<Point> result = new ArrayList<>();
        float angleStart = (float) (((src.direction - (src.fov/2)) * Math.PI)/180);
        for (int i = 0; i < src.rays; i++) {
            Point target = new Point((int)(src.positionX+Math.cos(src.anglePerRay*i + angleStart) * src.distance),
                    (int)(src.positionY+Math.sin(src.anglePerRay*i + angleStart) * src.distance), src.direction);
            //above returns a list of all the points around the mouse 800 units away will need to
            Point position = new Point((int) src.positionX,(int) src.positionY);
            LineSegment ray = new LineSegment(position,target,0);
            Point ci = RayCast.getClosestIntersection(ray, LineSegments, this, src.direction, src.fov, 'H');
            if (ci == null) {result.add(target);} else {result.add(ci);}
        }
        return result;//B list of all points that the rays intersect with
    }

    public ArrayList<Point> castRaysZ(Zombie src){

        ArrayList<Point> result = new ArrayList<>();
        float angleStart = (float) (((src.direction - (src.fov/2)) * Math.PI)/180);
        for (int i = 0; i < src.rays; i++) {
            Point target = new Point((int)(src.positionX+Math.cos(src.anglePerRay*i + angleStart) * src.distance),
                    (int)(src.positionY+Math.sin(src.anglePerRay*i + angleStart) * src.distance), src.direction);
            //above returns a list of all the points around the mouse 800 units away will need to
            Point position = new Point((int) src.positionX,(int) src.positionY);
            LineSegment ray = new LineSegment(position,target,0);
            Point ci = RayCast.getClosestIntersection(ray, LineSegments, this, src.direction, src.fov, 'Z');
            if (ci == null) {result.add(target);} else {result.add(ci);}
        }
        return result;//B list of all points that the rays intersect with
    }

    public void reset() {
        healthHum = 3;
        healthZom = 1;
        scoreHum = 0;
        scoreZom = 0;
    }

    private void collisions(){
        for (Zombie z : zombies){
            if (RayCast.CirclesCollision(human1.positionX, human1.positionY, human1.size, z.positionX, z.positionY, z.size)) {
                human1.health -= 1;
            }
            if (bullets != null) {
                for (Bullet b : bullets) {
                    if (RayCast.CirclesCollision(b.positionX, b.positionY, b.size, z.positionX, z.positionY, z.size)) {
                        b.alive = false;
                        z.health -= 1;
                    }
                }
            }
        }
        if (bullets != null) {
            bullets.removeIf(b -> !b.alive);
        }
        if (zombies.size() > 0) {
            zombies.removeIf(z -> z.health < 1);
        }
    }
}