package AI;
import RayCastCore.Game;
import RayCastCore.Human;
import RayCastCore.LineSegment;
import RayCastCore.Zombie;

import java.util.ArrayList;

public class AiScoring {
    public int HumanScore;
    public int ZombieScore;

    void update() {
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