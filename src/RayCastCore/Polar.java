package RayCastCore;

public class Polar {//angle only
    double angle;
    double r;
    Point point = new Point(0,0);

    public Polar(Point agentPos, Point rayPos){
        Point origin = point.subtract(agentPos, rayPos);
        this.r = Math.sqrt(Math.pow(origin.x,2) + Math.pow(origin.y,2));
        double temp = Math.toDegrees(Math.atan2(-origin.y, origin.x));
        if (temp < 0){
            this.angle = 360 - Math.abs(360 - 180 + temp);
        }else{
            this.angle = 180 - temp;
        }
    }
}