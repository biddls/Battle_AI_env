import java.util.ArrayList;
import java.util.Random;

public class Game {
    int windowX = 680;
    int windowY = 410;
    public int zombieCount = 2;
    public Human human1 = new Human(300, 150, 5, 10);
    public ArrayList<LineSegment> LineSegments;
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Zombie> zombies = new ArrayList<>();

    public Game(ArrayList<LineSegment> walls){
        this.LineSegments = walls;
        for(int i = 0; i < zombieCount; i++) {
            zombies.add(new Zombie(spawnPoint(), 1, 14));
        }
    }

    private Point spawnPoint(){

        Random rnd = new Random();

        Point gen1 = new Point(rnd.ints(1,20,windowY- 20), 20);
        Point gen2 = new Point(windowX - 20, rnd.ints(1,20,windowY -20));
        Point gen3 = new Point(rnd.ints(1,20,windowY), windowY -20);
        Point gen4 = new Point(20, rnd.ints(1,20,windowY - 20));
        int choose = rnd.nextInt(5);

        switch (choose) {
            case 1:
                return gen1;
            case 2:
                return gen2;
            case 3:
                return gen3;
            case 4:
                return gen4;
        }

        return gen2;
    }

    public void fired(){
        bullets.add(new Bullet(human1.positionX, human1.positionY, human1.direction));
    }

    public void update(){
        if (bullets.size() > 0) {
            for (Bullet b : bullets){
                b.update(LineSegments);
            }
            bullets.removeIf(b -> b.health < 1);
        }
        if (human1.health > 0) {
            human1.currentRays = human1.castRays(LineSegments, this);
        }
        if (zombies.size() > 0) {
            for (Zombie z : zombies) {
                z.currentRays = z.castRays(LineSegments, this);
                if (RayCast.CirclesCollision(human1.positionX, human1.positionY, human1.size, z.positionX, z.positionY, z.size)) {
                    human1.health -= 1;
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
        checkForDead();
    }

    private void checkForDead(){
        if (bullets != null) {
            bullets.removeIf(b -> b.health < 1);
        }
        if (zombies.size() > 0) {
            zombies.removeIf(z -> z.health < 1);
        }
    }
}