package listeners;

import gui.MainFrame;
import models.Tag;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TagListener implements MouseListener {

    private MainFrame mainFrame;

    public TagListener(MainFrame frame) {
        this.mainFrame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String label = ((JLabel) e.getSource()).getText();

        for (Tag tag : this.mainFrame.getData().getTags()) {
            if (tag.getTagName().equals(label)) {
                this.mainFrame.getTagsPressed().add(label);
                break;
            }
        }

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
