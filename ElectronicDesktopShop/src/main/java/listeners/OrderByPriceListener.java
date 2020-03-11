package listeners;

import interfaces.postEventHandlers.OrderByPricePostEventHandler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OrderByPriceListener implements MouseListener {

    private OrderByPricePostEventHandler frame;

    public OrderByPriceListener(OrderByPricePostEventHandler frame) {
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.frame.setDefaultOrderByName(false);
        this.frame.setDefaultOrderByPrice(true);
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
