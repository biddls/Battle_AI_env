import javax.swing.*;

public class Main {
    RayCastVisualizer rcv = new RayCastVisualizer();

    public static void main(String[] args) {
        Main main = new Main(1);
    }

    public Main(int mode){
        System.out.println("Mode selected" + mode);
        rcv.main();
    }
}
