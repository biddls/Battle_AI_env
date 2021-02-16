import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import FPS.Convert2Dto3D;
import FPS.GameFPS;
import RayCastCore.LineSegment;
import RayCastCore.Point;

public class FPSVisualiser extends JPanel implements MouseMotionListener, MouseListener, KeyListener {

    //RayCast.Human human1 = new RayCast.Human();
    public static final double RANGE = 800;
    public GameFPS env;
    char key;
    int addOrTake;
    public Robot robot = null;

    public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public static int screen_Width = dim.width;
    public static int screen_Height = dim.height;

    public static void RCV() {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame();
            FPSVisualiser rcv = new FPSVisualiser();
            window.setTitle("RayCast.RayCast Visualizer FPS");
            window.setSize(screen_Width, screen_Height);
            window.addKeyListener(rcv);
            window.addMouseListener(rcv);
            window.add(rcv);
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setFocusable(true);
            window.setExtendedState(Frame.MAXIMIZED_BOTH);
            window.setUndecorated(true);
            window.setVisible(true);
        });
    }

    public FPSVisualiser(){
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        initPolygons();
        initSegments();
        env = new GameFPS(activeSegments, 5);
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        addMouseMotionListener(this);
        addMouseListener(this);
        repaint();
    }

    ArrayList<Polygon> activePolygons = new ArrayList<>();
    ArrayList<Color> activePolygonsColours = new ArrayList<>();
    public void initPolygons(){

        //Border Polygon
        Polygon b = new Polygon();
        b.addPoint(10,10);
        b.addPoint(640,10);
        b.addPoint(640,360);
        b.addPoint(10,360);
        activePolygons.add(b);
        activePolygonsColours.add(new Color(113, 168, 50));

        Polygon p1 = new Polygon();
        p1.addPoint(100,150);
        p1.addPoint(120,50);
        p1.addPoint(200,80);
        p1.addPoint(140,210);
        activePolygons.add(p1);
        activePolygonsColours.add(new Color(168, 97, 50));

        Polygon p2 = new Polygon();
        p2.addPoint(100,200);
        p2.addPoint(120,250);
        p2.addPoint(60,300);
        activePolygons.add(p2);
        activePolygonsColours.add(new Color(168, 50, 154));

        Polygon p3 = new Polygon();
        p3.addPoint(200,260);
        p3.addPoint(220,150);
        p3.addPoint(300,200);
        p3.addPoint(350,320);
        activePolygons.add(p3);
        activePolygonsColours.add(new Color(50, 168, 123));

        Polygon p4 = new Polygon();
        p4.addPoint(340,60);
        p4.addPoint(360,40);
        p4.addPoint(370,70);
        activePolygons.add(p4);
        activePolygonsColours.add(new Color(50, 78, 168));


        Polygon p5 = new Polygon();
        p5.addPoint(450,190);
        p5.addPoint(560,170);
        p5.addPoint(540,270);
        p5.addPoint(430,290);
        activePolygons.add(p5);
        activePolygonsColours.add(new Color(123, 50, 168));

        Polygon p6 = new Polygon();
        p6.addPoint(400,95);
        p6.addPoint(580,50);
        p6.addPoint(480,150);
        p6.addPoint(400,95);
        activePolygons.add(p6);
        activePolygonsColours.add(new Color(50, 109, 168));
    }

    public ArrayList<LineSegment> activeSegments = new ArrayList<>();
    public void initSegments(){
//        for(Polygon p : activePolygons){
        for(int index = 0; index < activePolygons.size(); index++){
            for(int i=0;i<activePolygons.get(index).npoints;i++){

                Point start = new Point(activePolygons.get(index).xpoints[i],activePolygons.get(index).ypoints[i]);
                Point end;
                if(i==activePolygons.get(index).npoints-1){
                    end = new Point(activePolygons.get(index).xpoints[0],activePolygons.get(index).ypoints[0]);
                }else{
                    end = new Point(activePolygons.get(index).xpoints[i+1],activePolygons.get(index).ypoints[i+1]);
                }
                LineSegment temp = new LineSegment(start,end,initType(activePolygons.get(index)));
                temp.colour = activePolygonsColours.get(index);
                activeSegments.add(temp);
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
        env.update();
        super.paint(g);
        int index = 0;
        g.setColor(Color.RED);
        g.fillRect(0,1080/2,1920,1080/2);
        g.setColor(Color.BLUE);
        g.fillRect(0,0,1920,1080/2);
        for (int point = 0; point < env.Player1.currentRays3D.size(); point++){
            LineSegment[] l = Convert2Dto3D.convert(env.Player1.currentRays3D.get(point), index, screen_Height);
            for (int obj = l.length-1; obj >= 0; obj--){
                if (l[obj] != null) {
                    switch (l[obj].type) {
                        case 1:
                            double scalar = (800 - l[obj].distance)/800;
                            g.setColor(new Color((int) (l[obj].colour.getRed()*scalar), (int) (l[obj].colour.getGreen()*scalar), (int) (l[obj].colour.getBlue()*scalar)));
                            break;
                        case 3:
                            g.setColor(Color.YELLOW);
                            break;
                        case 4:
                            g.setColor(Color.GREEN);
                            break;
                    }
                    g.drawLine(index, (int) l[obj].A.y, index, (int) l[obj].B.y);
                }
            }
            index++;
        }
        g.setColor(Color.CYAN);
        g.fillRect((1920/2)-10, (1080/2)-2, 20, 4);
        g.fillRect((1920/2)-2, (1080/2)-10, 4, 20);

        //handle firing stuff
        if(addOrTake > -1 && env.Player1.health > 0){
            env.Player1.Mov(key, activeSegments, addOrTake);
            if (env.Player1.firing == 1){
                env.fired();
                env.Player1.firing = -1;
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
        env.Player1.firing = 0;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        env.Player1.Turn(1920/2 - e.getX());
        robot.mouseMove(1920/2, 1080);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        env.Player1.Turn(1920/2 - e.getX());
        robot.mouseMove(1920/2, 1080);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1){
            env.Player1.Fire(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1){
            env.Player1.Fire(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}