package listeners;

import interfaces.postEventHandlers.ChangeCountPostEventHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChangeCountListener implements KeyListener {

    private ChangeCountPostEventHandler globalShoppingCarListener;

    public ChangeCountListener(ChangeCountPostEventHandler globalShoppingCarListener) {
        this.globalShoppingCarListener = globalShoppingCarListener;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            globalShoppingCarListener.changeCount();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
