package listeners;

import interfaces.postEventHandlers.SubTagPostEventHandler;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static global.Constants.TAG_SUBTAG_SEPARATOR;

public class SubTagListener implements MouseListener {

    private SubTagPostEventHandler frame;

    public SubTagListener(SubTagPostEventHandler frame) {
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String label = ((JLabel) e.getSource()).getText();

        String[] split = label.trim().split(TAG_SUBTAG_SEPARATOR);
        frame.setTagPressed(split[0]);
        frame.setSubTagPressed(split[1]);
        frame.setSubTagPressed(true);

        frame.createAndShowGUI();
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
