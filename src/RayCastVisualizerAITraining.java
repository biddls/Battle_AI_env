import AI.AIGame;
import AI.Pair;
import FPS.FPSZombie;
import RayCastCore.Bullet;
import RayCastCore.LineSegment;
import RayCastCore.Point;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RayCastVisualizerAITraining extends JPanel {

    public AIGame env;
    char key;
    int addOrTake;

    public static void RCV(Pair pair, boolean newNeeded) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame();
            RayCastVisualizerAITraining rcv = null;
            try {
                rcv = new RayCastVisualizerAITraining(pair, newNeeded);
            } catch (Exception e) {
                e.printStackTrace();
            }
            window.setTitle("RayCast.RayCast Visualizer");
            window.setSize(680, 410);
            window.add(rcv);
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setVisible(true);
            window.setFocusable(true);
        });
    }

    public RayCastVisualizerAITraining(Pair pair, boolean newNeeded) throws Exception {
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        initPolygons();
        initSegments();
        env = new AIGame(activeSegments, 5, 100, pair, newNeeded);
        repaint();
    }

    ArrayList<Polygon> activePolygons = new ArrayList<>();
    public void initPolygons(){

        //Border Polygon
        Polygon b = new Polygon();
        b.addPoint(10,10);
        b.addPoint(640,10);
        b.addPoint(640,360);
        b.addPoint(10,360);
        activePolygons.add(b);

        Polygon p1 = new Polygon();
        p1.addPoint(100,150);
        p1.addPoint(120,50);
        p1.addPoint(200,80);
        p1.addPoint(140,210);
        activePolygons.add(p1);

        Polygon p2 = new Polygon();
        p2.addPoint(100,200);
        p2.addPoint(120,250);
        p2.addPoint(60,300);
        activePolygons.add(p2);

        Polygon p3 = new Polygon();
        p3.addPoint(200,260);
        p3.addPoint(220,150);
        p3.addPoint(300,200);
        p3.addPoint(350,320);
        activePolygons.add(p3);
        //activeAgents.add(p3);

        Polygon p4 = new Polygon();
        p4.addPoint(340,60);
        p4.addPoint(360,40);
        p4.addPoint(370,70);
        activePolygons.add(p4);

        Polygon p5 = new Polygon();
        p5.addPoint(450,190);
        p5.addPoint(560,170);
        p5.addPoint(540,270);
        p5.addPoint(430,290);
        activePolygons.add(p5);

        Polygon p6 = new Polygon();
        p6.addPoint(400,95);
        p6.addPoint(580,50);
        p6.addPoint(480,150);
        p6.addPoint(400,95);

        activePolygons.add(p6);
    }

    public ArrayList<LineSegment> activeSegments = new ArrayList<>();
    public void initSegments(){
        for(Polygon p : activePolygons){
            for(int i=0;i<p.npoints;i++){

                RayCastCore.Point start = new RayCastCore.Point(p.xpoints[i],p.ypoints[i]);
                RayCastCore.Point end;
                if(i==p.npoints-1){
                    end = new Point(p.xpoints[0],p.ypoints[0]);
                }else{
                    end = new RayCastCore.Point(p.xpoints[i+1],p.ypoints[i+1]);
                }
                activeSegments.add(new LineSegment(start,end,initType(p)));
            }
        }
    }

    int initType(Polygon shape) {//0 is nothing, 1 is wall, 2 is agent, 3 is for bullet, 4 is zombie
        if(activePolygons.contains(shape)){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public void paint(Graphics g) {
        int offset = 2;
        try {
            env.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.paint(g);

        g.setColor(Color.WHITE);
        for(Polygon p : activePolygons){
            g.drawPolygon(p);
        }

        //handle firing stuff
        if(addOrTake > -1 && env.aiHuman.health > 0){
            env.aiHuman.Mov(key, activeSegments, addOrTake);
            if (env.aiHuman.firing == 1){
                env.fired();
                env.aiHuman.firing = -1;
            }
        }

        //render bullets
        g.setColor(Color.YELLOW);
        for (Bullet b : env.bullets) {
            g.fillOval((int) b.positionX - b.size / 2, (int) b.positionY - b.size / 2, b.size, b.size);
        }

        if (env.aiHuman.health > 0) {
            //render the rays
            g.setColor(Color.RED);
            Point p;
            for (int point = 0; point < env.aiHuman.currentRays3D.size(); point++) {
                p = env.aiHuman.currentRays3D.get(point)[0];
                // JOSEPH U CAN FIND WHAT U NEED FROM THE HUMAN POV HERE
                g.drawLine((int) env.aiHuman.positionX, (int) env.aiHuman.positionY, (int) p.x, (int) p.y);
            }

            //drawing the human
            g.setColor(Color.WHITE);
            g.fillOval((int) env.aiHuman.positionX - env.aiHuman.size / 2, (int) env.aiHuman.positionY - env.aiHuman.size / 2, env.aiHuman.size, env.aiHuman.size);

            g.setColor(Color.RED);
            g.fillOval((int) env.aiHuman.positionX - env.aiHuman.size / 2 + offset / 2, (int) env.aiHuman.positionY - env.aiHuman.size / 2 + offset / 2, env.aiHuman.size - offset, env.human1.size - offset);
        }

        //drawing the zombies
        if (env.fpsZombies.size() > 0) {
            env.fpsZombies.get(0).Mov(key, activeSegments, addOrTake);
            for (FPSZombie z : env.fpsZombies) {
                g.setColor(Color.WHITE);
                g.fillOval((int) z.positionX - z.size / 2, (int) z.positionY - z.size / 2, z.size, z.size);

                g.setColor(Color.GREEN);
                g.fillOval((int) z.positionX - z.size / 2 + offset / 2, (int) z.positionY - z.size / 2 + offset / 2, z.size - offset, z.size - offset);
                Point p;
                for (int point = 0; point < z.currentRays3D.size(); point++) {
                    p = z.currentRays3D.get(point)[0];
                    // JOSEPH U CAN FIND WHAT U NEED FROM THE ZOMBIE POV HERE
                    g.setColor(Color.GREEN);
                    g.drawLine((int) z.positionX, (int) z.positionY, (int) p.x, (int) p.y);
                }
            }
        }

        //auto slow down to make the game easier to use
        try {
            Thread.sleep(5); // slow execution of the game
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addOrTake = 2;

        repaint();
    }
}

//todo:
//This is the structure of the AI process that needs to get implemented:
//
//Start of game:
//	load AIs or init them if not available
//
//Main game loop:
//	get observation from env
//	pass though AI
//	send output to env and loop
//
//End of game:
//	get score from env
//	save AIs (human and zombie)
//
//In between games:
//	Cull the weak
//	Breed the best AIs together
//
//Back to top