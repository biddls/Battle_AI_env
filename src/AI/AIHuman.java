package AI;
import FPS.Player;
import FerrantiM1.Acti;
import FerrantiM1.Layer;
import FerrantiM1.ModelSequential;
import RayCastCore.LineSegment;

import java.sql.Array;
import java.util.ArrayList;

// Add some shit in here to store the AI

public class AIHuman extends Player{
    public AIHuman(Player x) {
        super((float) x.positionX, (float) x.positionY, x.health, x.size, x.rays);
    }
    ArrayList<ObsStep> obs = new ArrayList<>();

    public void updatePlayer()  {
        for (int i=0; i<=currentRays3D.size()-1; i++) {
             obs.add(new ObsStep(currentRays3D.get(i)[0].type,currentRays3D.get(i)[0].distance));
        }
        try {
            model(obs);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void model(ArrayList<ObsStep> observation) throws Exception {
        ModelSequential model = new ModelSequential(new Layer[]{
                Layer.FullyConnected(5, Acti.relu()),
                Layer.FullyConnected(4, Acti.sigmoid()),
                Layer.FullyConnected(3, Acti.sigmoid()),
                Layer.FullyConnected(2, Acti.sigmoid())});

        model.RandomizeInit(model.getModel());
    }


    public void Turn(int x){
        this.direction -= (double) x/4;
    }

    public void Mov(ArrayList<Integer> NNout, ArrayList<LineSegment> segments) {
        // i recommend using tanH for the output layer because it can go from -1 to 1 and if u wanna press forward
        // 1 then u dont wanna also be pressing backwards -1 (just thought it would make ur life easier
        //[turn left, forward, strafe left, fire]

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

        Fire(NNout.get(3) == 1);
    }
}
