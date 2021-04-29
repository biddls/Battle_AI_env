package AI;
import FPS.FPSZombie;
import RayCastCore.LineSegment;

import java.util.ArrayList;

public class AIZombie extends FPSZombie{

    // Add some shit in here to store the AI

    public AIZombie(double x, double y, int health, int size, int rays) {
        super(x, y, health, size, rays);
    }

// update function here



    public void Turn(int x){
        this.direction -= (double) x/4;
    }

    public void Mov(ArrayList<Integer> NNout, ArrayList<LineSegment> segments) {

        //[turn left, forward, strafe left]

        int forwardbackwards = NNout.get(1);
        int strafeLeftRight = NNout.get(2);

        double cos = Math.cos(Math.toRadians(direction));
        double cos90 = Math.cos(Math.toRadians(90 - direction));
        double sin = Math.sin(Math.toRadians(direction));
        double sin90 = Math.sin(Math.toRadians(90 - direction));

        double x = (forwardbackwards * (cos)) + (strafeLeftRight * (cos90));
        double y = (forwardbackwards * (sin)) + (strafeLeftRight * -(sin90));

        // may need to add in a normalisation step here but ye
        Turn(NNout.get(0));

        collisionCheck(segments, x, y);
    }
}
