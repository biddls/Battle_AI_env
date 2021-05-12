package AI;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class Pair {
    public String human;
    public String zombie;

    public Pair(Integer HumanScore, Integer ZombieScore) {
        this.human = HumanScore + ".BJ";
        this.zombie = ZombieScore + ".BJ";
    }

    public Pair() {
        // ignore its supposed to be empty to fill a gap
    }

    public static List<Pair> pairAndClean(List<Integer> humansClean, List<Integer> zombiesClean) {
        List<Pair> pairs = new ArrayList<>();
        // tidying
        humansClean = new ArrayList<>(new LinkedHashSet<>(humansClean));
        zombiesClean = new ArrayList<>(new LinkedHashSet<>(zombiesClean));
        Collections.sort(humansClean);
        Collections.reverse(humansClean);
        Collections.sort(zombiesClean);
        Collections.reverse(zombiesClean);
        // just for checking will remove
        System.out.println(humansClean);
        System.out.println(zombiesClean);
        int depth = Math.min(humansClean.size(), zombiesClean.size());
        int height = Math.max(humansClean.size(), zombiesClean.size());
        // make the best pair up
        for (int i=0; i<height; i++){
            if (i < depth){
                pairs.add(new Pair(humansClean.get(i), zombiesClean.get(i)));
            } else {
                // remove the unpair-able to prevent any miss matching
                File file = new File("/Score/Human/" + humansClean.get(i) + ".BJ");
                file.delete();
                file = new File("/Score/Zombie/" + zombiesClean.get(i) + ".BJ");
                file.delete();
            }
        }
        return pairs;
    }
}
