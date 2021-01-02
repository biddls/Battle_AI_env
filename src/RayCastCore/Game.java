package RayCastCore;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    int windowX = 620;
    int windowY = 340;
    public int zombieCount;
    public Human human1 = new Human(300, 150, 5, 10);
    public ArrayList<LineSegment> LineSegments;
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Zombie> zombies = new ArrayList<>();


    public Game(ArrayList<LineSegment> walls, int zombiesToSpawn){
        this.LineSegments = walls;
        this.zombieCount = zombiesToSpawn;
        for(int i = 0; i < zombieCount; i++) {
            zombies.add(new Zombie(spawnPoint(), 1, 14));
        }
    }

    private Point spawnPoint(){

        Random rnd = new Random();
        int choose = rnd.nextInt(5);
        switch (choose) {
            case 1:
                int pnt1 = rnd.nextInt(windowX -20 );
                return new Point(pnt1+20 , 20);
            case 2:
                int pnt2 =  rnd.nextInt(windowY-20);
                return new Point(windowX ,pnt2 + 20);
            case 3:
                int pnt3 = rnd.nextInt(windowX-20);
                return new Point(pnt3+20, windowY-20);
            case 4:
                int pnt4 = rnd.nextInt(windowY-20);
                return new Point(20,pnt4+20 );
        }

        return new Point(50,50);
    }

    public void fired(){
        bullets.add(new Bullet(human1.positionX, human1.positionY, human1.direction, 6, 2));
    }

    public void update(){
        if (bullets.size() > 0) {
            for (Bullet b : bullets){
                b.collisionCheck(LineSegments, b.cos, b.sin);
            }
            bullets.removeIf(b -> b.health < 1);
        }
        if (human1.health > 0) {
            human1.currentRays = human1.castRays(LineSegments, this);
        }
        if (zombies.size() > 0) {
            for (Zombie z : zombies) {
                if (z.health > 0) {
                    z.currentRays = z.castRays(LineSegments, this);
                    if (RayCast.CirclesCollision(human1.positionX, human1.positionY, human1.size, z.positionX, z.positionY, z.size)) {
                        human1.health -= 1;
                    }
                }
                if (bullets != null) {
                    for (Bullet b : bullets) {
                        if (RayCast.CirclesCollision(b.positionX, b.positionY, b.size, z.positionX, z.positionY, z.size)) {
                            b.health -= 1;
                            z.health -= 1;
                        }
                    }
                }
            }
        }
        assert bullets != null;
        if (bullets.size() > 0) {
            bullets.removeIf(b -> b.health < 1);
        }
        if (zombies.size() > 0) {
            zombies.removeIf(z -> z.health < 1);
        }
    }
}