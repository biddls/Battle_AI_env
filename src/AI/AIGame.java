package AI;
import FPS.FPSZombie;
import FPS.GameFPS;
import FerrantiM1.ModelSequential;
import RayCastCore.Bullet;
import RayCastCore.LineSegment;
import RayCastCore.RayCast;
import java.util.ArrayList;

public class AIGame extends GameFPS{

    public ModelSequential zombieModel;
    public ModelSequential humanModel;
    public AIHuman aiHuman;
    public AiScoring score; // human is being "1" zombie is being "2"
    public ArrayList<AIZombie> AIZombies = new ArrayList<>();
    public double pastDistance;

    public AIGame(ArrayList<LineSegment> walls, int zombiesToSpawn, int rays, Pair pair, boolean newNeeded) throws Exception {
        super(walls, zombiesToSpawn, rays);
        this.aiHuman = new AIHuman(this.Player1);
        for (FPSZombie z : this.fpsZombies) {
            AIZombies.add(new AIZombie(z));
            initModels(pair, newNeeded);
        }
    }

    @Override
    public void update() throws Exception {
        score.update();
        if (bullets.size() > 0) {
            for (Bullet b : bullets){
                b.collisionCheck(LineSegments, b.cos, b.sin);
            }
            bullets.removeIf(b -> b.health < 1);
        }
        if (aiHuman.health > 0) {
            aiHuman.currentRays3D = aiHuman.castRays3D(LineSegments, this);
            aiHuman.updatePlayer();
        }
        if (AIZombies.size() > 0) {

            for (AIZombie z : AIZombies) {
                if (z.health > 0) {
                    z.currentRays3D = z.castRays3D(LineSegments, this);
                    if (RayCast.CirclesCollision(aiHuman.positionX, aiHuman.positionY, aiHuman.size, z.positionX, z.positionY, z.size)) {
                        aiHuman.health -= 1;
                        score.lostLife(1);
                    }
                    for(int i=0; i < z.currentRays3D.toArray().length; i++) {
                        if (z.currentRays3D.get(i)[0].type == 4) {
                            if(pastDistance != 0 && z.currentRays3D.get(i)[0].distance < pastDistance){
                                score.zombieApproach();
                            }
                            pastDistance = z.currentRays3D.get(i)[0].distance;
                        }
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
        if (AIZombies.size() > 0) {
            AIZombies.removeIf(z -> z.health < 1);
            score.lostLife(2);
            for (AIZombie zombie: AIZombies) {
                zombie.Mov(zombieModel.inference(zombie.update()), this.LineSegments);
            }
        }
    }

    public void initModels(Pair pair, boolean newNeeded) throws Exception {
        // define the models shape here
        if (!newNeeded){
            zombieModel.load(pair.zombie);
            humanModel.load(pair.human);
        } else {
            zombieModel.RandomizeInit(zombieModel.getModel());
            humanModel.RandomizeInit(humanModel.getModel());
        }
    }

    public void saveModels() throws Exception {
        // this will be integrated with what joseph is doing with the save model he can just copy this code where ever he needs it
        this.zombieModel.Mutate(1); // will need to think about this
        this.zombieModel.save(1);// doo what u gotta do joey boiii
        this.humanModel.Mutate(1); // will need to think about this
        this.humanModel.save(1);// doo what u gotta do joey boiii
    }
}
