import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyPress extends KeyAdapter {
    public char keyp;
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyTyped(e);
        keyp = e.getKeyChar();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        keyp = '0';
    }
}

