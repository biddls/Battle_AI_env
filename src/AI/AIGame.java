package AI;
import FPS.FPSZombie;
import FPS.GameFPS;
import RayCastCore.Bullet;
import RayCastCore.LineSegment;
import RayCastCore.RayCast;

import java.util.ArrayList;

public class AIGame extends GameFPS{

    public AIHuman aiHuman;

    public AIGame(ArrayList<LineSegment> walls, int zombiesToSpawn, int rays) {
        super(walls, zombiesToSpawn, rays);
        this.aiHuman = new AIHuman(this.Player1);
    }

    @Override
    public void update() {
        if (bullets.size() > 0) {
            for (Bullet b : bullets){
                b.collisionCheck(LineSegments, b.cos, b.sin);
            }
            bullets.removeIf(b -> b.health < 1);
        }
        if (aiHuman.health > 0) {
            aiHuman.currentRays3D = aiHuman.castRays3D(LineSegments, this);
        }
        if (fpsZombies.size() > 0) {
            for (FPSZombie z : fpsZombies) {
                if (z.health > 0) {
                    z.currentRays3D = z.castRays3D(LineSegments, this);
                    if (RayCast.CirclesCollision(aiHuman.positionX, aiHuman.positionY, aiHuman.size, z.positionX, z.positionY, z.size)) {
                        aiHuman.health -= 1;
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
