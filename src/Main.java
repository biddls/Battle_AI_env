import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Main main = new Main(1);
    }

    public Main(int mode){
        switch(mode) {
            case 0:
                RayCastVisualizer.RCV();
                break;
            case 1:
                RayCastVisualizerAITraining.RCV();
                break;
            case 2:
                FPSVisualiser.RCV();
                break;

        }



    }
}
