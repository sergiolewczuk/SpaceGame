package input;

import java.awt.event.*;

public class KeyBoard implements KeyListener {

    private boolean[] keys = new boolean[256];

    public static boolean UP, LEFT, RIGHT, SPACE, SHOOT, SHOOT_SPEED_ADD, SHOOT_SPEED_DELETE;

    public KeyBoard() {

        UP = false;
        LEFT = false;
        RIGHT = false;
        SPACE = false;
        SHOOT = false;
        SHOOT_SPEED_ADD = false;
        SHOOT_SPEED_DELETE = false;

    }

    public void update(){
        UP = keys[KeyEvent.VK_UP];
        LEFT = keys[KeyEvent.VK_LEFT];
        RIGHT = keys[KeyEvent.VK_RIGHT];
        SPACE = keys[KeyEvent.VK_SPACE];
        SHOOT = keys[KeyEvent.VK_P];
        SHOOT_SPEED_ADD = keys[KeyEvent.VK_ADD];
        SHOOT_SPEED_DELETE = keys[KeyEvent.VK_SUBTRACT];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
