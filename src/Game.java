import java.awt.*;
import java.util.ArrayList;

public class Game {
    public int healthHum = 3;
    public int healthZom = 1;
    public int ZomReach = 5;
    public int scoreHum = 0;
    public int scoreZom = 0;
    public int damage = 1;
    public int magazine = 8;
    public Human human1 = new Human();
    public ArrayList<LineSegment> LineSegments = new ArrayList<>();
    public ArrayList<Bullet> bullets = new ArrayList<>();

    public Game(ArrayList<LineSegment> walls){
        this.LineSegments = walls;
    }

    public void fired(){
        bullets.add(new Bullet(human1.positionX, human1.positionY, human1.direction));
    }

    public void update(){
        bullets.removeIf(b -> b.update(LineSegments));
        //do bullet zombie collision stuff
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
