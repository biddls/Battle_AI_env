import java.awt.*;
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
            zombies.add(new Zombie());
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
        human1.currentRays = castRays(human1);
        zombies.get(0).currentRays = castRaysZ(zombies.get(0));
    }

    public ArrayList<Point> castRays(Human src){

        ArrayList<Point> result = new ArrayList<>();
        float angleStart = (float) (((src.direction - (src.fov/2)) * Math.PI)/180);
        for (int i = 0; i < src.rays; i++) {
            Point target = new Point((int)(src.positionX+Math.cos(src.anglePerRay*i + angleStart) * src.distance),
                    (int)(src.positionY+Math.sin(src.anglePerRay*i + angleStart) * src.distance), src.direction);
            //above returns a list of all the points around the mouse 800 units away will need to
            Point position = new Point((int) src.positionX,(int) src.positionY);
            LineSegment ray = new LineSegment(position,target,0);
            Point ci = RayCast.getClosestIntersection(ray, LineSegments, this, src.direction, src.fov);
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
            Point ci = RayCast.getClosestIntersection(ray, LineSegments, this, src.direction, src.fov);
            if (ci == null) {result.add(target);} else {result.add(ci);}
        }
        return result;//B list of all points that the rays intersect with
    }

    public void handleHealth(int health, char Agent) {
        switch (Agent) {
            case 'A':
                healthHum -= health;
                System.out.println("agent A took" + health + "damage" );
                scoreZom += 1;
                break;
            case 'B':
                healthZom -= health;
                System.out.println("agent B took" + health + "damage" );
                scoreHum += 1;
                break;
        }
    }

    public void hit(Point AgentA, Point AgentB, char attacked) {
        if(AgentA.x - AgentB.x < ZomReach && AgentA.y - AgentB.y < ZomReach){
            handleHealth(damage,attacked);
        }
    }

    public void reset() {
        healthHum = 3;
        healthZom = 1;
        scoreHum = 0;
        scoreZom = 0;
    }

    public void shot(boolean hit, char attacked) {
        if(magazine == 0){
            int count = 0;
            while((count!= 100)){ // supposed to shoot soldier from shooting
                count++;
            }
            magazine = 8;
        }
        if (hit) {
            System.out.println("soldier hit" + attacked);
            magazine -= 1;
            handleHealth(damage, attacked);
        } else {
            System.out.println("soldier missed");
            magazine -= 1;
        }
    }

    public void Claw() {
        System.out.println("attack");
    }


}