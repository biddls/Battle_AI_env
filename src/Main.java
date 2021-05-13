import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
;

public class Main {
    // 0 = 2d 2 player PVP
    // 1 = AI Training Mode (No renderer)
    // 2 = 3D Visualiser Mode
    public static void main(String[] args) {
        new Main();
    }

    public Main(){
        JFrame f=new JFrame();//creating instance of JFrame

        JLabel title = new JLabel("Battle Arena Environment");
        title.setBounds(125,5,250,40);
        f.add(title);

        String introS = "Hello and welcome to the Battle arena Test environment\nMade by Joseph and Thomas for our end of year project." +
                "\nWe have 4 different modes for you to try.\nEach environment will have a set of instructions provided\na little bit of additional " +
                "information and instructions\nso be sure to check it out first";
        JTextArea intro = new JTextArea(introS);
        intro.setEditable(false);
        intro.setBounds(20,45,350,80);
        f.add(intro);

        JLabel op1 = new JLabel("2-D Top Down Shooter");
        op1.setBounds(20,130, 150,40);
        f.add(op1);

        JButton op1Instr = new JButton("Instructions");
        op1Instr.setBounds(20,175,110,40);
        op1Instr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog Instr = new JDialog();

                String words = "the 2-D top down shooter was a basic test for our environment\nit was a basis for setting out most of the basic functionality\n" +
                        "using the ray cast system getting the collision system happy\nand basic game mechanics. Before moving onto more completed\nsystems.\n" +
                        "The basic buttons you need to know are WASD and Q,E the former\n for strafing the human up, down, left and right.\n The Q and the E are for rotation, left and right. Space is for shooting" +
                        "\nYou may notice the rays are still there,\n this was a debugging step for our vision system.\n because it looks cool if a little messy";
                JTextArea instructions = new JTextArea(words);
                instructions.setEditable(false);
                instructions.setBounds(20,45,250,80);

                Instr.add(instructions);
                Instr.setSize(400,250);
                Instr.setVisible(true);
            }
        });
        f.add(op1Instr);

        JButton op1Open = new JButton("Run Mode 0");
        op1Open.setBounds(150,175,110,40);
        op1Open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clicked(0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        f.add(op1Open);

        JLabel op2 = new JLabel("AI Training Mode");
        op2.setBounds(20,220, 150,40);
        f.add(op2);

        JButton op2Instr = new JButton("Instructions");
        op2Instr.setBounds(20,260,110,40);
        op2Instr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog Instr = new JDialog();

                String words = "The AI training Module was intially going to be for us to train the humans and the\n" +
                        " zombies for use in a seperate environment similar to mode 1 however when we ran into the\n" +
                        " issues you will see shortly in training we realised a rewrite of the entire program would\n" +
                        " be required to fix the issue. There are no specific instruction you require for this. just\n" +
                        " watch for as long as you want i suppose.... P.S its a bit like watching paint dry.\n" +
                        " Dont tell Tom i said that though. ";
                JTextArea instructions = new JTextArea(words);
                instructions.setEditable(false);
                instructions.setBounds(20,45,350,80);

                Instr.add(instructions);
                Instr.setSize(550,250);
                Instr.setVisible(true);
            }
        });
        f.add(op2Instr);

        JButton op2Open = new JButton("Run Mode 1");
        op2Open.setBounds(150,260,110,40);
        op2Open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clicked(1);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        f.add(op2Open);

        JLabel op3 = new JLabel("3-D FPS");
        op3.setBounds(20,295, 150,40);
        f.add(op3);

        JButton op3Instr = new JButton("Instructions");
        op3Instr.setBounds(20,330,110,40);
        op3Instr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog Instr = new JDialog();

                String words = "Here is the 3-D FPS mode, mirroring the same environment from mode 0,\n" +
                        " A clever little secret being it isnt actually 3-D. IT's still 2-D we just use a clever trick\n" +
                        " to make it look 3-D thats why you cant move your cursor up or down. Its the same technique\n" +
                        " that was used in the original doom 3-D.\n" +
                        "For instructions your WASD keys are for movement and your mouse controls the rotation,\n" +
                        "clicking will fire the gun. to exit you will need to use Alt-F4.";
                JTextArea instructions = new JTextArea(words);
                instructions.setEditable(false);
                instructions.setBounds(20,45,350,80);

                Instr.add(instructions);
                Instr.setSize(550,250);
                Instr.setVisible(true);
            }
        });
        f.add(op3Instr);

        JButton op3Open = new JButton("Run Mode 2");
        op3Open.setBounds(150,330,110,40);
        op3Open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clicked(2);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        f.add(op3Open);

        JLabel op4 = new JLabel("AI Test Script");
        op4.setBounds(20,370, 150,40);
        f.add(op4);

        JButton op4Instr = new JButton("Instructions");
        op4Instr.setBounds(20,415,110,40);
        op4Instr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog Instr = new JDialog();

                String words = "This is a simple test instruction, Just to show some basic view of how our \n" +
                        "actual AI system performs and how it does what it do.";
                JTextArea instructions = new JTextArea(words);
                instructions.setEditable(false);
                instructions.setBounds(20,45,350,80);

                Instr.add(instructions);
                Instr.setSize(550,250);
                Instr.setVisible(true);
            }
        });
        f.add(op4Instr);

        JButton op4Open = new JButton("Run Mode 3");
        op4Open.setBounds(150,415,110,40);
        op4Open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clicked(3);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        f.add(op4Open);

        JLabel op5 = new JLabel("It's getting late so you might have to re-open after each one. Sorry ...not sorry");
        op5.setBounds(10,400, 450,140);
        f.add(op5);
        
        f.setSize(470,550);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void clicked(int action) throws Exception {
        switch(action) {
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
            case 3:
                System.out.println("running Test Script");
                Test test = new Test();
                test.TestScript();
        }
    }
}
