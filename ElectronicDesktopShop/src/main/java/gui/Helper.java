package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Helper {

    public static void getJLabelFromString(ArrayList<JLabel> productNamesAsJLabels, String name) {
        JLabel label = new JLabel(name);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 15));
        productNamesAsJLabels.add(label);
    }
}
