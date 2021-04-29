package AI;
import RayCastCore.Game;
import RayCastCore.LineSegment;
import java.util.ArrayList;

public class AiScoring extends Game {
    public int HumanScore;
    public int ZombieScore;
    public int round;
    public boolean humanWon = false;
    public boolean zombiesWon = false;
    public AiScoring(ArrayList<LineSegment> walls, int zombiesToSpawn, int round) {
        super(walls, zombiesToSpawn);
        this.round = round;
    }

    @Override
    public void update() {
        System.out.println(round);
        if (human1.health > 0 && zombies.size() > 0) {
            score();
        }else if (human1.health > 0){
            humanWon = true;//move to next level
        }else if (zombies.size() > 0){
            zombiesWon = true;//end and send scores back
        }
    }

    void score() {
        HumanScore += 0.05;
        ZombieScore -= 0.05;
        //human
            //not losing lives
            //killing zombies
            //not hitting walls
        // zombie
            //closing in on human
            //damaging human
            //not getting hit
    }
}