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
                for(int i=1; i<=950; i++) {
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
                System.out.println("Mode 2 initializing");
                FPSVisualiser.RCV();
                break;
        }
    }

    public static void stats(int type, int number) {

        switch(type) {
            case 0:
                Threads += number;

                break;
            case 1:
                Games += number;
                break;
            case 2:
                CompletedGames += number;
        }



        if(Threads >= 940 && !initialized) {
            System.out.println("Mode 1 Started Successfully");
            initialized =true;
        }

        if(Games >= 940 && !Started) {
            System.out.println("All Games have started successfully");
            Started = true;
        }
        if(CompletedGames >= 940) {
            System.out.println("All Games have finished successfully. Updating weights and restarting environment...");
        }

    }
}
