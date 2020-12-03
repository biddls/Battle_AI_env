import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by Armin on 9/21/2017.
 */
public class RayCastVisualizer extends JPanel implements KeyListener {

    agent Agent = new agent();
    char key;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame window = new JFrame();
                RayCastVisualizer rcv = new RayCastVisualizer();
                window.setTitle("RayCast Visualizer");
                window.setSize(657,400);
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
        init();
    }

    ArrayList<Polygon> activePolygons = new ArrayList<>();
    ArrayList<Polygon> activeAgents = new ArrayList<>();
    public void initPolygons(){

        //Border Polygon
        Polygon b = new Polygon();
        b.addPoint(0,0);
        b.addPoint(640,0);
        b.addPoint(640,360);
        b.addPoint(0,360);
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
        activeAgents.add(p3);

        Polygon p4 = new Polygon();
        p4.addPoint(340,60);
        p4.addPoint(360,40);
        p4.addPoint(370,70);
        activePolygons.add(p4);

        Polygon p5 = new Polygon();
        //p5.addPoint(450,190);
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
                //System.out.println(initType(p));
                //System.out.println("new segment : " + start + " -> " + end);
            }
        }
    }

    void init() {

        currentRays = castRays(Agent, 800);//B number of rays and how far to check
        repaint();
    }

    int initType(Polygon shape) {
        if(activePolygons.contains(shape)){
            if (activeAgents.contains(shape)){
                return 2;
            }else{
                return 1;
            }
        }else{
            return 0;
        }
    }

    ArrayList<Point> currentRays = new ArrayList<>();

    public ArrayList<Point> castRays(agent src,int dist){//TODO where in the int n fed in from (line 110 i found)

        ArrayList<Point> result = new ArrayList<>();
        float angletart = (float) (((src.direction - (src.fov/2)) * Math.PI)/180);
        for (int i = 0; i < src.rays; i++) {//TODO: given the characters angle loop though certain angles
            //(B) System.out.println(i);
            Point target = new Point((int)(src.positionX+Math.cos(src.anglePerRay*i + angletart)*dist),
                    (int)(src.positionY+Math.sin(src.anglePerRay*i + angletart)*dist), src.direction);
            //above returns a list of all the points around the mouse 800 units away will need to TODO: adapt this to be based on character DIR
            Point position = new Point((int) src.positionX,(int) src.positionY);
            LineSegment ray = new LineSegment(position,target,0);
            Point ci = RayCast.getClosestIntersection(ray,activeSegments);
            if(ci != null) {result.add(ci);}
            else {result.add(target);}
        }
        return result;//B list of all points that the rays intersect with
    }



    @Override
    public void paint(Graphics g) {//TODO: add characters
        super.paint(g);

        g.setColor(Color.WHITE);
        for(Polygon p : activePolygons){
            g.drawPolygon(p);
        }

        g.setColor(Color.GREEN);
        for(Point p : currentRays){
            g.drawLine((int) Agent.positionX,(int) Agent.positionY, p.x,p.y);
            g.fillOval( p.x-5,p.y-5,10,10);
        }
        g.setColor(Color.BLUE);
        g.fillOval((int) Agent.positionX - Agent.size/2, (int) Agent.positionY - Agent.size/2, Agent.size, Agent.size);
        if(key != 0){
            Agent.agentMov(key, activeSegments);
            repaint();
        }
        currentRays = castRays(Agent, 800);//B number of rays and how far to check

        for (Point ray :currentRays) {
            if(ray.type == 2){
                System.out.println(ray);
            }

        }

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
       key = e.getKeyChar();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        key= 0;
        repaint();


    }


}



