import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Main main = new Main(1);
    }

    public Main(int mode){
        System.out.println("Mode selected" + mode);
        RayCastVisualizer.RCV();
        RayCastVisualizerAITraining.RCV();
        FPSVisualiser.RCV();
    }
}
