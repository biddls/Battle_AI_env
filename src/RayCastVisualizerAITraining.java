import RayCastCore.Game;
import RayCastCore.LineSegment;
import RayCastCore.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;



public class RayCastVisualizerAITraining {

    //RayCast.Human human1 = new RayCast.Human();
    public static final double RANGE = 800;
    public Game env;
    char key;
    int addOrTake;
    int loop = 0;
    public static void RCV() {
            RayCastVisualizerAITraining rcv = new RayCastVisualizerAITraining();
           }

    public RayCastVisualizerAITraining(){
        initPolygons();
        initSegments();
        env = new Game(activeSegments, 5);
        Main.stats(0,1);
        loop();
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

    int initType(Polygon shape) {//0 is nothing, 1 is wall, 2 is agent, 3 is for bullet, 4 is zombie
        if(activePolygons.contains(shape)){
            return 1;
        }else{
            return 0;
        }
    }

    public void loop(){
        boolean loops = true;
        env.paintCount++;

        while(loops == true){
            env.update();
            //handle firing stuff
            if(addOrTake > -1 && env.human1.health > 0){
                env.human1.Mov(key, activeSegments, addOrTake);
                if (env.human1.firing == 1){
                    env.fired();
                    env.human1.firing = -1;
                }
            }
            if(loop == 0){
                Main.stats(1,1);
                loop +=1;
            }

        }
    }
}