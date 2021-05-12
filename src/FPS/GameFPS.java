package FPS;
import RayCastCore.Bullet;
import RayCastCore.LineSegment;
import RayCastCore.RayCast;
import RayCastCore.Zombie;

import java.util.ArrayList;

public class GameFPS extends RayCastCore.Game {

    public ArrayList<FPSZombie> fpsZombies = new ArrayList<>();
    public Player Player1;

    public GameFPS(ArrayList<LineSegment> walls, int zombiesToSpawn, int rays) {
        super(walls, zombiesToSpawn);
        this.Player1 = new Player(300, 150, 5, 10, rays);
        if (this.zombies.size() > 0) {
            for (Zombie z : this.zombies) {
                fpsZombies.add(new FPSZombie(z.positionX, z.positionY, z.health, z.size, z.rays));
                //float x, float y, int health, int size, int rays
            }
        }
    }

    @Override
    public void fired(){
        bullets.add(new Bullet(Player1.positionX, Player1.positionY, Player1.direction, 6, 2));
    }

    @Override
    public void update() throws Exception {
        if (bullets.size() > 0) {
            for (Bullet b : bullets){
                b.collisionCheck(LineSegments, b.cos, b.sin);
            }
            bullets.removeIf(b -> b.health < 1);
        }
        if (Player1.health > 0) {
            System.out.println(Player1.health);
            Player1.currentRays3D = Player1.castRays3D(LineSegments, this);


        }
        if (fpsZombies.size() > 0) {
            for (FPSZombie z : fpsZombies) {
                if (z.health > 0) {
                    z.currentRays3D = z.castRays3D(LineSegments, this);
                    if (RayCast.CirclesCollision(Player1.positionX, Player1.positionY, Player1.size, z.positionX, z.positionY, z.size)) {
                        Player1.health -= 1;
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
        if (fpsZombies.size() > 0) {
            fpsZombies.removeIf(z -> z.health < 1);
        }
    }
}