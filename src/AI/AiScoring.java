package AI;
import RayCastCore.Game;
import RayCastCore.LineSegment;
import java.util.ArrayList;

public class AiScoring extends Game {
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

        }else if (human1.health > 0){
            humanWon = true;//move to next level
        }else if (zombies.size() > 0){
            zombiesWon = true;//end and send scores back
        }
    }
}