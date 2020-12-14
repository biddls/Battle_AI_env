import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Zombie {

        public float positionX = 500;
        public float positionY = 300;
        public float direction = 0;//degrees
        public int size = 10;
        public float rays = 50;
        public float fov = 90;
        public float anglePerRay = (float) 0;
        private final int[] DIMENSIONS = {10 + (size / 2), 10 + (size / 2), 640 - (this.size / 2), 360 - (size / 2)};
        private ArrayList pressing = new ArrayList<>();

        public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list){
            Set<T> set = new LinkedHashSet<>(list);
            return new ArrayList<T>(set);
        }

        public void ZombieMov(char keyPressed, ArrayList<LineSegment> segments, int addOrTake) {//1 is add 0 is take
            if (addOrTake > -1) {
                pressing = removeDuplicates(pressing);
                if (addOrTake == 1 && !pressing.contains(keyPressed)) {//add
                    pressing.add(keyPressed);
                } else if (addOrTake == 0) {//take
                    if (pressing.contains(keyPressed)) {
                        pressing.remove((Object) keyPressed);
                    }
                }

                anglePerRay = (float) (((fov * Math.PI) / 180) / rays);

                int w = pressing.contains('w') ? 1 : 0;
                int a = pressing.contains('a') ? 1 : 0;
                int s = pressing.contains('s') ? 1 : 0;
                int d = pressing.contains('d') ? 1 : 0;
                int q = pressing.contains('q') ? 1 : 0;
                int e = pressing.contains('e') ? 1 : 0;

//            ArrayList<Integer> onOrOff = new ArrayList<>(Arrays.asList(w, a, s, d, q, e));

                this.direction = (q == 1 ^ e == 1) ? (q * (this.direction - 1)) + (e * (this.direction + 1)) : this.direction;

                double dir = Math.toRadians(direction);
                double dir90 = Math.toRadians(90-direction);
                double cos = Math.cos(dir);
                double cos90 = Math.cos(dir90);
                double sin = Math.sin(dir);
                double sin90 = Math.sin(dir90);

                double x = (w * (cos)) + (a * (cos90)) + (s * -(cos)) + (d * -(cos90));
                double y = (w * (sin)) + (a * -(sin90)) + (s * -(sin)) + (d * (sin90));

                collisionCheck(segments, x, y);
            }
        }

        private void collisionCheck(ArrayList<LineSegment> segments, double changeX, double changeY){
            float startX = positionX;
            float startY = positionY;
            this.positionX += (float) changeX;
            this.positionY += (float) changeY;

            positionX = (positionX > DIMENSIONS[2]) ? DIMENSIONS[2] : positionX;
            positionY = (positionY > DIMENSIONS[3]) ? DIMENSIONS[3] : positionY;
            positionX = (positionX < DIMENSIONS[0]) ? DIMENSIONS[0] : positionX;
            positionY = (positionY < DIMENSIONS[1]) ? DIMENSIONS[1] : positionY;

            if (startX != positionX || startY != positionY) {
                double perpendicular;
                for (int i = 0; i < segments.size(); i++) {//goes through all segments
                    LineSegment segment = segments.get(i);//gets a segment
                    if (segment.angleDeg != 0) {//for all lines that arnt vertical
                        perpendicular = segment.angleRad - (Math.PI / 2); //-90 degrees basically to get the perpendicular
                        Point a = new Point((startX + size / 2 * Math.cos(perpendicular)), (startY + size / 2 * Math.sin(perpendicular)));//a point out in front
                        Point b = new Point((startX - size / 2 * Math.cos(perpendicular)), (startY - size / 2 * Math.sin(perpendicular)));//a point beind
                        //these 4 lines ^ create a line that is perpendicular to the line it is near
                        Point intersect = RayCast.intersectLines(new LineSegment(a, b, 1), segment);
                        if (BetweenX(intersect, segment) && BetweenY(intersect, segment)) {
                            if (RayCast.distance(intersect, positionX, positionY) <= 6) {
                                this.positionX = (float) (startX - (2 * changeX));
                                this.positionY = (float) (startY - (2 * changeY));
                            }
                        }
                    }
                }
            }
        }

        private boolean BetweenX(Point intersection, LineSegment segment){
            SimplePoint inter = intersection.getPoint();
            int min = Math.min(segment.A.x, segment.B.x);
            int max = Math.max(segment.A.x, segment.B.x);
            if(min <= inter.x && inter.x <= max){
                return true;
            }
            return false;
        }

        private boolean BetweenY(Point intersection, LineSegment segment){
            SimplePoint inter = intersection.getPoint();
            int min = Math.min(segment.A.y, segment.B.y);
            int max = Math.max(segment.A.y, segment.B.y);
            if(min <= inter.y && inter.y <= max){
                return true;
            }
            return false;
        }


}
