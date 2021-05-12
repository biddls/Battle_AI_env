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
            scores();
        }else if (human1.health > 0){
            humanWon = true;//move to next level
        }else if (zombies.size() > 0){
            zombiesWon = true;//end and send scores back
        }
    }

    void scores() {
        HumanScore += 0.08;
        ZombieScore -= 0.12;
    }

    public void lostLife(int being) {
        switch(being) {
            case 1:
                HumanScore -= 3;
                ZombieScore += 10;
                break;
            case 2:
                HumanScore += 0.7;
                ZombieScore -= 1;
                break;

        }
    }

    public void TimeOut() {
        HumanScore -= 15;
        ZombieScore -= 15;
    }

    // unsure if required, will leave in in case the ai keep getting stuck to the walls
    public void wallCollision(int being) {
        switch(being) {
            case 1:
                HumanScore -= 0.5;
                break;
            case 2:
                HumanScore -= 0.4;
                break;

        }
    }

    public void zombieApproach() {

        ZombieScore += 0.2;
        HumanScore -= 0.3;
    }



}