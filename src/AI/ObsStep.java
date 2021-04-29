package AI;

public class ObsStep {

    public int type;
    public double distance;

    public ObsStep(int type, double distance) {
        this.type = type;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "(" + type + "|" + distance + ")";
    }
}
