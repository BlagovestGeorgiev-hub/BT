package listeners;

import interfaces.postEventHandlers.PayButtonPostEventHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayButtonActionListener implements ActionListener {

    private PayButtonPostEventHandler handler;

    public PayButtonActionListener(PayButtonPostEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handler.constructInvoice();
    }
}
