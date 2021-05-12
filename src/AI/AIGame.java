package AI;
import FPS.FPSZombie;
import FPS.GameFPS;
import FerrantiM1.Acti;
import FerrantiM1.Layer;
import FerrantiM1.ModelSequential;
import FerrantiM1.Matrix;
import RayCastCore.*;
import java.time.Clock;
import java.time.Duration;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class AIGame extends GameFPS{

    public ModelSequential zombieModel;
    public ModelSequential humanModel;
    public AIHuman aiHuman;
    public Pair NewAI;
    public Timer tick = new Timer();
    TimerTask tt;
    double time;
    public AiScoring score = new AiScoring(); // human is being "1" zombie is being "2"
    public ArrayList<AIZombie> AIZombies = new ArrayList<>();
    public double pastDistance;

    public AIGame(ArrayList<LineSegment> walls, int zombiesToSpawn, int rays, Pair pair, boolean newNeeded) throws Exception {
        super(walls, zombiesToSpawn, rays);

        this.NewAI = pair;
        this.aiHuman = new AIHuman(this.Player1);
        for (FPSZombie z : this.fpsZombies) {

            AIZombies.add(new AIZombie(z));
            initModels(pair, newNeeded);
        }
        tt = new TimerTask() {
            @Override
            public void run() {
                time +=0.001;
            }
        };
        tick.schedule(this.tt,1);
    }


    @Override
    public void update() throws Exception {
        System.out.println("running");
        score.update();

        if(time == 180) {
            aiHuman.health = 0;
        }

        if (bullets.size() > 0) {
            for (Bullet b : bullets){
                b.collisionCheck(LineSegments, b.cos, b.sin);
            }
            bullets.removeIf(b -> b.health < 1);
        }
        if (aiHuman.health > 0) {
            aiHuman.currentRays3D = aiHuman.castRays3D(LineSegments, this);
            aiHuman.Mov(humanModel.inference(aiHuman.update()), this.LineSegments);
        } else {
            this.saveModels(score.HumanScore, score.ZombieScore);
            //end Thread?
        }
        if (AIZombies.size() > 0) {
            for (AIZombie z : AIZombies) {
                if (z.health > 0) {
                    z.currentRays3D = z.castRays3D(LineSegments, this);
                    if (RayCast.CirclesCollision(aiHuman.positionX, aiHuman.positionY, aiHuman.size, z.positionX, z.positionY, z.size)) {
                        aiHuman.health -= 1;
                        score.TimeOut();
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
        } else if(round <= 3) {
            round ++;
            for (FPSZombie z : this.fpsZombies) {
                AIZombies.add(new AIZombie(z));
                initModels(this.NewAI, false);
            }

        } else {
            this.saveModels(score.HumanScore, score.ZombieScore);
            //end Thread?
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
        zombieModel = new ModelSequential(new Layer[]{
                Layer.FullyConnected(500, Acti.leakyrelu()),
                Layer.FullyConnected(300, Acti.sigmoid()),
                Layer.FullyConnected(150, Acti.leakyrelu()),
                Layer.FullyConnected(50, Acti.leakyrelu()),
                Layer.FullyConnected(10, Acti.sigmoid()),
                Layer.FullyConnected(3, Acti.tanh())});
        humanModel = new ModelSequential(new Layer[]{
                Layer.FullyConnected(500, Acti.leakyrelu()),
                Layer.FullyConnected(300, Acti.sigmoid()),
                Layer.FullyConnected(150, Acti.leakyrelu()),
                Layer.FullyConnected(50, Acti.leakyrelu()),
                Layer.FullyConnected(10, Acti.sigmoid()),
                Layer.FullyConnected(4, Acti.tanh())});
        if (!newNeeded){
            zombieModel.load(pair.zombie);
            humanModel.load(pair.human);
        } else {
            zombieModel.RandomizeInit(zombieModel.getModel());
            humanModel.RandomizeInit(humanModel.getModel());
        }
    }

    public void saveModels(int humanScore, int zombieScore) throws Exception {
        // this will be integrated with what joseph is doing with the save model he can just copy this code where ever he needs it
        this.zombieModel.Mutate(1); // will need to think about this
        this.zombieModel.save("/Score/Zombie/", zombieScore);
        this.humanModel.Mutate(1); // will need to think about this
        this.humanModel.save("/Score/Human/", humanScore);
        // ../Score/Human || ../Score/Zombie
    }
}
