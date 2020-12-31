package FPS;

import RayCastCore.Bullet;
import RayCastCore.LineSegment;
import RayCastCore.RayCast;
import RayCastCore.Zombie;

import java.util.ArrayList;

public class GameFPS extends RayCastCore.Game {

    public Player Player1 = new Player(300, 150, 5, 10);

    public GameFPS(ArrayList<LineSegment> walls, int zombiesToSpawn) {
        super(walls, zombiesToSpawn);
    }

    @Override
    public void fired(){
        bullets.add(new Bullet(Player1.positionX, Player1.positionY, Player1.direction, 6, 2));
    }

    @Override
    public void update() {
        if (bullets.size() > 0) {
            for (Bullet b : bullets){
                b.collisionCheck(LineSegments, b.cos, b.sin);
            }
            bullets.removeIf(b -> b.health < 1);
        }
        if (Player1.health > 0) {
            Player1.currentRays3D = Player1.castRays3D(LineSegments, this);
        }
        if (zombies.size() > 0) {
            for (Zombie z : zombies) {
                if (z.health > 0) {
                    z.currentRays = z.castRays(LineSegments, this);
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
        if (zombies.size() > 0) {
            zombies.removeIf(z -> z.health < 1);
        }
    }
}