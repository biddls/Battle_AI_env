import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
//note to see if it working

public class DrawRect extends JPanel {//sets size fo box
    private static final int RECT_X = 20;
    private static final int RECT_Y = RECT_X;
    private static final int RECT_WIDTH = 512;
    private static final int RECT_HEIGHT = RECT_WIDTH;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw the rectangle here
        g.drawRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
        for (int i = 0; i < 15; i++){
            int randomSizeX = ThreadLocalRandom.current().nextInt(i * 5, 100 + (2 * i));
            int randomSizeY = ThreadLocalRandom.current().nextInt(i * 5, 100 + (2 * i));
            int randomPlaceX = ThreadLocalRandom.current().nextInt(RECT_X, RECT_WIDTH + 1);
            int randomPlaceY = ThreadLocalRandom.current().nextInt(RECT_Y, RECT_HEIGHT + 1);
            g.drawRect(randomPlaceX, randomPlaceY, randomSizeX, randomSizeY);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        // so that our GUI is big enough
        return new Dimension(RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
    }

    // create the GUI explicitly on the Swing event thread
    private static void createAndShowGui() {
        DrawRect mainPanel = new DrawRect();

        JFrame frame = new JFrame("DrawRect");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }
}