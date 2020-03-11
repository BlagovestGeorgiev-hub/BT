package gui;

import global.Constants;
import interfaces.TextParser;
import models.DataHolder;
import models.SubTag;
import models.Tag;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShopTagsAndSubTagsScreenImpl {

    private MainFrameImpl mainFrame;

    public ShopTagsAndSubTagsScreenImpl(MainFrameImpl mainFrame) {

        this.mainFrame = mainFrame;

    }

    public JPanel constructShopTagsAndSubTags(ArrayList<String> tagsPressed) {
        TextParser textParser = mainFrame.getTextParser();
        textParser.readData();
        mainFrame.setData(textParser.getData());
        ArrayList<JLabel> tagsAsLabels = new ArrayList<>();
        createJLabelsFromTags(mainFrame.getData(), tagsAsLabels);

        JPanel container = new JPanel();
        BoxLayout boxLayout = new BoxLayout(container, BoxLayout.Y_AXIS);
        container.setLayout(boxLayout);

        for (JLabel label : tagsAsLabels) {
            label.addMouseListener(mainFrame.getTagListener());
            container.add(label);
            container.setAlignmentX(Component.LEFT_ALIGNMENT);
            createJLabelsFromSubTags(tagsPressed, mainFrame.getData(), container, label);
        }
        container.setBorder(BorderFactory.createLineBorder(Color.black));
        return container;
    }

    private void createJLabelsFromTags(DataHolder data, ArrayList<JLabel> tagsAsLabels) {
        for (Tag tag : data.getTags()) {
            Helper.getJLabelFromString(tagsAsLabels, tag.getTagName());
        }
    }

    private void createJLabelsFromSubTags(ArrayList<String> tagsPressed, DataHolder data, JPanel container, JLabel label) {
        if (tagsPressed.contains(label.getText())) {
            data.getTags().forEach(t -> {
                if (t.getTagName().equals(label.getText())) {
                    ArrayList<SubTag> subTags = t.getSubTags();
                    for (SubTag subTag : subTags) {
                        JLabel subTagLabel = new JLabel(Constants.SUB_TAG_PREFIX + label.getText() + Constants.TAG_SUBTAG_SEPARATOR + subTag.getTagName());
                        subTagLabel.setFont(new Font(subTagLabel.getFont().getName(), Font.BOLD, 15));
                        subTagLabel.addMouseListener(mainFrame.getSubTagListener());
                        container.add(subTagLabel);
                        container.setAlignmentX(Component.LEFT_ALIGNMENT);
                    }
                }
            });
        }
    }
}
