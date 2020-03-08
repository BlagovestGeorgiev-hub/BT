package listeners;

import gui.MainFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static global.Constants.TAG_SUBTAG_SEPARATOR;

public class SubTagListener implements MouseListener {

    private MainFrame mainFrame;

    public SubTagListener(MainFrame frame) {
        this.mainFrame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String label = ((JLabel) e.getSource()).getText();

        String[] split = label.trim().split(TAG_SUBTAG_SEPARATOR);
        mainFrame.setTagPressed(split[0]);
        mainFrame.setSubTagPressed(split[1]);
        mainFrame.setSubTagPressed(true);

        mainFrame.createAndShowGUI();
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
