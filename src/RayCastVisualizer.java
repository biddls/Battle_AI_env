

import com.javacodegeeks.snippets.desktop.agent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Armin on 9/21/2017.
 */
public class RayCastVisualizer extends JPanel implements KeyEvent {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("RayCast Visualizer");
        window.setSize(640,380);

        RayCastVisualizer rcv = new RayCastVisualizer();

        window.add(rcv);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
    agent agent1 = new agent();

    public RayCastVisualizer(){
        this.setBackground(Color.BLACK);

        this.setLayout(null);

        initPolygons();
        initSegments();
        agent1.agentMov('w');

    }

    ArrayList<Polygon> activePolygons = new ArrayList<>();//TODO: we could add something here to set the polygon type and then as teh character moves we update their position in this?
    private void initPolygons(){//TODO: change this to randomly generate a map

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



    ArrayList<LineSegment> activeSegments = new ArrayList<>();
    private void initSegments(){
        for(Polygon p : activePolygons){
            for(int i=0;i<p.npoints;i++){

                Point start = new Point(p.xpoints[i],p.ypoints[i]);
                Point end;
                if(i==p.npoints-1){
                    end = new Point(p.xpoints[0],p.ypoints[0]);
                }else{
                    end = new Point(p.xpoints[i+1],p.ypoints[i+1]);
                }
                activeSegments.add(new LineSegment(start,end));
                //System.out.println("new segment : " + start + " -> " + end);
            }
        }

    }



    ArrayList<Point> currentRays = new ArrayList<>();

    @Override
    public void keyTyped(KeyEvent event) {
        System.out.println("SUP BOIIIIIIIIIII");
        char ch = event.getKeyChar();
        agent1.agentMov(ch);
        currentRays = castRays(agent1,800);//B number of rays and how far to check
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("i release the key by the power of christ and anime");
    }

    public ArrayList<Point> castRays(agent src,int dist){//TODO where in the int n fed in from (line 110 i found)
        ArrayList<Point> result = new ArrayList<>();
        float angletart = (float) (((src.direction - (src.fov/2)) * Math.PI)/180);
        for (int i = 0; i < src.rays; i++) {//TODO: given the characters angle loop though certain angles
            //(B) System.out.println(i);
            Point target = new Point((int)(src.positionX+Math.cos(src.anglePerRay*i + angletart)*dist),(int)(src.positionY+Math.sin(src.anglePerRay*i + angletart)*dist));
            //above returns a list of all the points around the mouse 800 units away will need to TODO: adapt this to be based on character DIR
            Point position = new Point(src.positionX, src.positionY);
            LineSegment ray = new LineSegment(position,target);
            Point ci = RayCast.getClosestIntersection(ray,activeSegments);
            if(ci != null) result.add(ci);
            else result.add(target);
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

        g.setColor(Color.RED);
        for(Point p : currentRays){
            g.drawLine((int) agent1.positionX,(int) agent1.positionY, (int)p.x, (int) p.y);
            g.fillOval((int) p.x-5,(int) p.y-5,10,10);
        }
    }
}
