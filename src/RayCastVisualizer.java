import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Armin on 9/21/2017.
 */

public class RayCastVisualizer extends JPanel implements KeyListener {

    //Human human1 = new Human();
    public static final double RANGE = 800;
    public Game env;
    char key;
    int addOrTake;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame window = new JFrame();
                RayCastVisualizer rcv = new RayCastVisualizer();
                window.setTitle("RayCast Visualizer");
                window.setSize(680, 410);
                window.addKeyListener(rcv);
                window.add(rcv);
                window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                window.setVisible(true);
                window.setFocusable(true);
            }
        });
    }

    public RayCastVisualizer(){
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        initPolygons();
        initSegments();
        env = new Game(activeSegments);
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

                Point start = new Point(p.xpoints[i],p.ypoints[i]);
                Point end;
                if(i==p.npoints-1){
                    end = new Point(p.xpoints[0],p.ypoints[0]);
                }else{
                    end = new Point(p.xpoints[i+1],p.ypoints[i+1]);
                }
                activeSegments.add(new LineSegment(start,end,initType(p)));
            }
        }
    }

    int initType(Polygon shape) {//0 is nothing, 1 is wall, 2 is agent, 3 is for bullet
        if(activePolygons.contains(shape)){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public void paint(Graphics g) {
        int offset = 2;
        env.update();
        super.paint(g);

        g.setColor(Color.WHITE);
        for(Polygon p : activePolygons){
            g.drawPolygon(p);
        }

        //handle firing stuff
        if(addOrTake > -1){
            env.human1.Mov(key, activeSegments, addOrTake);
            if (env.human1.firing == 1){
                env.fired();
                env.human1.firing = -1;
            }
        }

        //render bullets
        g.setColor(Color.YELLOW);
        for (Bullet b : env.bullets) {
            g.fillOval((int) b.positionX - b.size / 2, (int) b.positionY - b.size / 2, b.size, b.size);

        }
        if (env.human1.health > 0) {
            //render the rays
            g.setColor(Color.RED);
            for (Point p : env.human1.currentRays) {
                SimplePoint P = new SimplePoint(p);
                g.drawLine((int) env.human1.positionX, (int) env.human1.positionY, (int) P.x, (int) P.y);
            }

            //drawing the human
            g.setColor(Color.WHITE);
            g.fillOval((int) env.human1.positionX - env.human1.size / 2, (int) env.human1.positionY - env.human1.size / 2, env.human1.size, env.human1.size);

            g.setColor(Color.RED);
            g.fillOval((int) env.human1.positionX - env.human1.size / 2 + offset / 2, (int) env.human1.positionY - env.human1.size / 2 + offset / 2, env.human1.size - offset, env.human1.size - offset);
        }

        //drawing the zombies
        if (env.zombies.size() > 0) {
            env.zombies.get(0).Mov(key, activeSegments, addOrTake);
            for (Zombie z : env.zombies) {
                g.setColor(Color.WHITE);
                g.fillOval((int) z.positionX - z.size / 2, (int) z.positionY - z.size / 2, z.size, z.size);

                g.setColor(Color.GREEN);
                g.fillOval((int) z.positionX - z.size / 2 + offset / 2, (int) z.positionY - z.size / 2 + offset / 2, z.size - offset, z.size - offset);
                for (Point r : z.currentRays) {
                    g.setColor(Color.GREEN);
                    SimplePoint P = new SimplePoint(r);
                    g.drawLine((int) z.positionX, (int) z.positionY, (int) P.x, (int) P.y);
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        key = e.getKeyChar();
        addOrTake = 1;
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        key = e.getKeyChar();
        addOrTake = 0;
        env.human1.firing = 0;
        repaint();
    }
}