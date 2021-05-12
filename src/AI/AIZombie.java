package AI;
import FPS.FPSZombie;
import FerrantiM1.Matrix;
import RayCastCore.Humanoid;
import RayCastCore.LineSegment;
import java.util.ArrayList;

public class AIZombie extends FPSZombie{

    public AIZombie(FPSZombie z) {
        super(z.positionX, z.positionY, z.health, z.size, z.rays);
    }

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
            obs[i+temp] = points[0].distance/distance;
            i += 5;
        }
        return new Matrix(1, this.rays * 2, new double[][]{obs});
    }


    public void Turn(double x){
        this.direction -= x;
    }

    public void Mov(Matrix NNout, ArrayList<LineSegment> segments) {

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
    }
}
