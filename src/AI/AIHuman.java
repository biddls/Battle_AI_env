package AI;
import FPS.Player;
import FerrantiM1.Acti;
import FerrantiM1.Layer;
import FerrantiM1.Matrix;
import FerrantiM1.ModelSequential;
import RayCastCore.LineSegment;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;

// Add some shit in here to store the AI

public class AIHuman extends Player{
    public AIHuman(Player x) {
        super((float) x.positionX, (float) x.positionY, x.health, x.size, x.rays);
    }
    ArrayList<ObsStep> obs = new ArrayList<>();

    public Matrix update() {
        double[] obs = new double[this.rays * 5];
        int i = 0;
        for (RayCastCore.Point[] points : currentRays3D) {
            int[] types = new int[]{0, 0, 0, 0, 0};
            types[points[0].type] = 1;
            int temp = 0;
            for (int index: types) {
                obs[i+temp] = index;
                temp++;
            }
            obs[i+temp-1] = points[0].distance/distance;
            i += 5;
        }
        return new Matrix(1, this.rays * 5, new double[][]{obs});
    }

    public void model(ArrayList<ObsStep> observation) throws Exception {
        ModelSequential model = new ModelSequential(new Layer[]{
                Layer.FullyConnected(5, Acti.relu()),
                Layer.FullyConnected(4, Acti.sigmoid()),
                Layer.FullyConnected(3, Acti.sigmoid()),
                Layer.FullyConnected(2, Acti.sigmoid())});

        model.RandomizeInit(model.getModel());
    }

    public void Turn(double x) { this.direction -= x; }

    public void Mov(Matrix NNout, ArrayList<LineSegment> segments) {
        // i recommend using tanH for the output layer because it can go from -1 to 1 and if u wanna press forward
        // 1 then u dont wanna also be pressing backwards -1 (just thought it would make ur life easier
        //[turn left, forward, strafe left, fire]

        //[turn, forward, strafe]
        double forwardsBackward1 = NNout.arr[0][0];
        double strafeLeftRight1 = NNout.arr[0][1];
        int forwardBackwards = (int) (Math.abs(forwardsBackward1)/forwardsBackward1);
        int strafeLeftRight = (int) (Math.abs(strafeLeftRight1)/strafeLeftRight1);

        double cos = Math.cos(Math.toRadians(direction));
        double cos90 = Math.cos(Math.toRadians(90 - direction));
        double sin = Math.sin(Math.toRadians(direction));
        double sin90 = Math.sin(Math.toRadians(90 - direction));

        double x = (forwardBackwards * (cos)) + (strafeLeftRight * (cos90));
        double y = (forwardBackwards * (sin)) + (strafeLeftRight * -(sin90));

        // may need to add in a normalisation step here but ye
        Turn(NNout.arr[0][2]);

        collisionCheck(segments, x, y);

        Fire(((int) (NNout.arr[0][3] + 1)/2) == 1);
    }
}
