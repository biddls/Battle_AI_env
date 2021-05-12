public class Main {
    // 0 = 2d 2 player PVP
    // 1 = AI Training Mode (No renderer)
    // 2 = 3D Visualiser Mode
    public static void main(String[] args) {
        new Main(1);
    }

    public static int Threads;
    public static int Games;
    public static boolean initialized;
    public static boolean Started;
    public static int CompletedGames;

    public Main(int mode){
        switch(mode) {
            case 0:
                System.out.println("Mode 0 initializing");
                RayCastVisualizer.RCV();
                break;
            case 1:
                System.out.println("Mode 1 initializing");
                AIManadgement.runAI(1);
                break;
            case 2:
                System.out.println("Mode 2 initializing");
                FPSVisualiser.RCV();
                break;
        }

    }

}
