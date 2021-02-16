import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        new Main(2);
    }

    public Main(int mode){
        switch(mode) {
            case 0:
                RayCastVisualizer.RCV();
                break;
            case 1:
                for(int i=1; i<=1000; i++) {
                    new Thread(() -> {
                        try{
                            RayCastVisualizerAITraining.RCV();
                        } catch(Exception e) {
                            System.out.println("An unknown exception :" + e.toString());
                        }
                    }).start();
                }
                break;
            case 2:
                FPSVisualiser.RCV();
                break;
        }
    }
}
