import AI.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AIManadgement {
    public static void runAI(int AIs) {
        while (true) {
            // get full lists and pair up the AIs
            List<Integer> humansClean = new ArrayList<Integer>();
            List<Integer> zombiesClean = new ArrayList<Integer>();
            File[] humans = new File("/Score/Human").listFiles();
            File[] zombies = new File("/Score/Zombie").listFiles();

            assert humans != null && zombies != null;
            for (File file : humans) {
                if (file.isFile()) {
                    humansClean.add(Integer.parseInt(Arrays.toString(file.getName().split(".BJ"))));
                }
            }
            for (File file : zombies) {
                if (file.isFile()) {
                    zombiesClean.add(Integer.parseInt(Arrays.toString(file.getName().split(".BJ"))));
                }
            }
            // pair up AIs for use
            List<Pair> paired = Pair.pairAndClean(humansClean, zombiesClean);
            if (paired.size() != 0 && paired.size() <= AIs) {// run normally
                for (Pair pair: paired) {
                    new Thread(() -> {
                        try {
                            RayCastVisualizerAITraining.RCV(pair, false);
                        } catch (Exception e) {
                            System.out.println("An unknown exception :" + e);
                        }
                    }).start();
                }
            } else {// if there are 0 then gen new ones
                for (int i = ((AIs - paired.size()) < AIs ? AIs - paired.size() : 0); i < AIs; i++){
                    new Thread(() -> {
                        try {
                            RayCastVisualizerAITraining.RCV(new Pair(), true);
                        } catch (Exception e) {
                            System.out.println("An unknown exception :" + e);
                        }
                    }).start();
                }
            }
        }
    }
}
