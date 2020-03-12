package listeners;

import interfaces.postEventHandlers.ChangeProductCountPostEventHandler;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChangeProductCountListener implements MouseListener {

    private ChangeProductCountPostEventHandler globalShoppingCarListener;

    public ChangeProductCountListener(ChangeProductCountPostEventHandler globalShoppingCarListener) {
        this.globalShoppingCarListener = globalShoppingCarListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            JLabel label = (JLabel) e.getSource();
            globalShoppingCarListener.reconstructDialog(label);
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
