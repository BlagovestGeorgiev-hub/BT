package listeners;

import interfaces.postEventHandlers.OrderByNamePostEventHandler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OrderByNameListener implements MouseListener {

    private OrderByNamePostEventHandler frame;

    public OrderByNameListener(OrderByNamePostEventHandler frame) {
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.frame.setDefaultOrderByName(true);
        this.frame.setDefaultOrderByPrice(false);
        this.frame.createAndShowGUI();
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
