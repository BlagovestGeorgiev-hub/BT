package listeners;

import interfaces.postEventHandlers.TagPostEventHandler;
import models.Tag;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TagListener implements MouseListener {

    private TagPostEventHandler frame;

    public TagListener(TagPostEventHandler frame) {
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String label = ((JLabel) e.getSource()).getText();

        for (Tag tag : this.frame.getData().getTags()) {
            if (tag.getTagName().equals(label)) {
                this.frame.getTagsPressed().add(label);
                break;
            }
        }

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
